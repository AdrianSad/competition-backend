package pl.adrian.competitionbackend.competition.service

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import pl.adrian.competitionbackend.competition.exception.model.AlreadyParticipateException
import pl.adrian.competitionbackend.competition.exception.model.CompetitionNotFoundException
import pl.adrian.competitionbackend.competition.exception.model.NotParticipateException
import pl.adrian.competitionbackend.competition.model.dto.CreateCompetitionDto
import pl.adrian.competitionbackend.competition.model.entity.Competition
import pl.adrian.competitionbackend.competition.repository.CompetitionRepository
import pl.adrian.competitionbackend.user.model.dto.UserDetailsImpl
import pl.adrian.competitionbackend.user.model.dto.UserInfo
import pl.adrian.competitionbackend.user.model.entity.User
import pl.adrian.competitionbackend.user.service.UserService
import java.time.LocalDate

@Service
class CompetitionService(private val competitionRepository: CompetitionRepository,
                         private val userService: UserService,
                         private val mongoTemplate: MongoTemplate) {

    fun createCompetition(createCompetitionDto: CreateCompetitionDto, user: UserDetailsImpl) : Competition {
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

    fun leaveCompetition(user: UserDetailsImpl, id: String): Competition {
        val competition = getCompetition(id);

        val userInfo = UserInfo.fromUserDetails(user)
        if(competition.users.any{it.id.equals(userInfo.id)}) {
            val competitions = competition.users.toMutableList()
            competitions.remove(userInfo);
            competition.users = competitions
            return competitionRepository.save(competition)
        } else throw NotParticipateException()
    }

    fun attendCompetition(userDetailsImpl: UserDetailsImpl, id: String): Competition {
        val competition = getCompetition(id);

        val userInfo = UserInfo.fromUserDetails(userDetailsImpl)
        return if(competition.users.any{it.id.equals(userInfo.id)}) {
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
        val user = competition.users.find { it.id.equals(userDetailsImpl.id) } ?: throw NotParticipateException()
        user.statistics[LocalDate.now()] = result
        competition.users.find { it.id.equals(userDetailsImpl.id) }?.statistics = user.statistics
        return competitionRepository.save(competition)
    }
}