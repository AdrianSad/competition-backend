package pl.adrian.competitionbackend.competition.service

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import pl.adrian.competitionbackend.competition.exception.model.AlreadyParticipateException
import pl.adrian.competitionbackend.competition.exception.model.CompetitionEndedException
import pl.adrian.competitionbackend.competition.exception.model.CompetitionNotFoundException
import pl.adrian.competitionbackend.competition.exception.model.NotParticipateException
import pl.adrian.competitionbackend.competition.model.dto.CreateCompetitionDto
import pl.adrian.competitionbackend.competition.model.entity.Competition
import pl.adrian.competitionbackend.competition.model.entity.Day
import pl.adrian.competitionbackend.competition.repository.CompetitionRepository
import pl.adrian.competitionbackend.user.model.dto.UserDetailsImpl
import pl.adrian.competitionbackend.user.model.dto.UserInfo
import pl.adrian.competitionbackend.user.model.entity.User
import pl.adrian.competitionbackend.user.service.UserService
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.ceil

@Service
class CompetitionService(private val competitionRepository: CompetitionRepository,
                         private val userService: UserService,
                         private val mongoTemplate: MongoTemplate) {

    fun createCompetition(createCompetitionDto: CreateCompetitionDto, user: UserDetailsImpl): Competition {
        val participants: MutableList<User> = userService.getUsersByUsername(createCompetitionDto.usernames.map { it.username }).toMutableList()
        val createdBy: User = userService.getUser(user.id.toString())
        participants.add(createdBy);

        return competitionRepository.save(Competition.toCompetition(createCompetitionDto,
                participants.map { UserInfo.fromUser(it) }.toList(),
                user.id.toString()))
    }

    fun getUserCompetitions(user: UserDetailsImpl): List<Competition> {
        val usernameCriteria = Criteria.where("username").isEqualTo(user.username)
        val userCriteria = Criteria.where("users").elemMatch(usernameCriteria)
        return mongoTemplate.find(Query.query(userCriteria), Competition::class.java)
    }

    fun getCompetition(id: String): Competition {
        val competition = competitionRepository.findById(id)
        if (competition.isPresent) {
            return competition.get()
        } else {
            throw CompetitionNotFoundException()
        }
    }

    fun getLabels(competition: Competition): MutableSet<LocalDate> {
        val labels = HashSet<LocalDate>()
        competition.users.map { it.statistics.keys }.forEach { labels.addAll(it) }
        return labels
    }

    fun getLabelsForWholeCompetition(competition: Competition): MutableSet<LocalDate> {
        val labels = HashSet<LocalDate>()
        competition.users.map { it.statistics.keys }.forEach { labels.addAll(it) }
        var datePlusOne: LocalDate? = labels.lastOrNull()
        if (datePlusOne != null) {
            while (datePlusOne!!.isBefore(competition.endDate?.minusDays(1))) {
                datePlusOne = datePlusOne.plusDays(1)
                labels.add(datePlusOne)
            }
        }
        return labels
    }

    fun leaveCompetition(user: UserDetailsImpl, id: String): Competition {
        val competition = getCompetition(id);

        val userInfo = UserInfo.fromUserDetails(user)
        if (competition.users.any { it.id.equals(userInfo.id) }) {
            val competitions = competition.users.toMutableList()
            competitions.remove(userInfo);
            competition.users = competitions
            return competitionRepository.save(competition)
        } else throw NotParticipateException()
    }

    fun attendCompetition(userDetailsImpl: UserDetailsImpl, id: String): Competition {
        val competition = getCompetition(id);

        val userInfo = UserInfo.fromUserDetails(userDetailsImpl)
        return if (competition.users.any { it.id.equals(userInfo.id) }) {
            throw AlreadyParticipateException()
        } else {
            val competitions = competition.users.toMutableList()
            competitions.add(userInfo);
            competition.users = competitions
            competitionRepository.save(competition)
        }
    }

    fun addUserResult(result: Int, userDetailsImpl: UserDetailsImpl, id: String): Competition {
        val competition = getCompetition(id)
        val userCompetition = competition.users.find { it.id.equals(userDetailsImpl.id) }
                ?: throw NotParticipateException()
        if (competition.endDate!!.isAfter(LocalDate.now())) {
            throw CompetitionEndedException()
        }
        if (userCompetition.statistics.isEmpty()) {
            userCompetition.statistics[LocalDate.now()] = Day(result, result)
        } else {
            if (userCompetition.statistics.lastEntry().key == LocalDate.now()) {
                userCompetition.statistics.pollLastEntry()
            }
            val sum: Int = userCompetition.statistics.lastEntry().value.sum + result
            userCompetition.statistics[LocalDate.now()] = Day(result, sum)
        }
        userCompetition.participatedDays = userCompetition.statistics.count()
        userCompetition.totalSum = userCompetition.statistics.values.map(Day::amount).sum()
        userCompetition.averageScore = userCompetition.statistics.values.map(Day::amount).average()
        competition.bestAverage = competition.users.maxOf(UserInfo::averageScore)
        competition.bestAverageUserId = competition.users.first { it.averageScore.equals(competition.bestAverage) }.id.toString()
        competition.users.find { it.id.equals(userDetailsImpl.id) }?.statistics = userCompetition.statistics
        competition.users.forEach { it.predictionData = getPredictionData(competition, it) }
        return competitionRepository.save(competition)
    }

    private fun getPredictionData(competition: Competition, userCompetition: UserInfo): NavigableMap<LocalDate, Day> {
        var predictionData = TreeMap<LocalDate, Day>()
        predictionData.putAll(userCompetition.statistics)
        if (StringUtils.hasText(competition.bestAverageUserId)) {
            val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), competition.endDate)
            val bestUserSum = competition.users.find { it.id.equals(competition.bestAverageUserId) }!!.totalSum
            val predictedMaxResult = bestUserSum + competition.bestAverage * daysLeft
            val predictedUserResult = predictedMaxResult - userCompetition.totalSum + 1
            ceil(predictedUserResult)
            val resultPerDay = predictedUserResult.toInt() / daysLeft.toInt()
            var datePlusOne: LocalDate = LocalDate.now()

            while (datePlusOne.isBefore(competition.endDate!!.minusDays(1))) {
                datePlusOne = datePlusOne.plusDays(1)
                if (predictionData.isEmpty()) {
                    predictionData[datePlusOne] = Day(resultPerDay, resultPerDay)
                } else {
                    val sum: Int = predictionData.lastEntry().value.sum + resultPerDay
                    predictionData[datePlusOne] = Day(resultPerDay, sum)
                }
            }
        }
        return predictionData
    }
}