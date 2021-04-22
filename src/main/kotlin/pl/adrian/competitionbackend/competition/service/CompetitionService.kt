package pl.adrian.competitionbackend.competition.service

import org.springframework.stereotype.Service
import pl.adrian.competitionbackend.competition.model.dto.CreateCompetitionDto
import pl.adrian.competitionbackend.competition.model.entity.Competition
import pl.adrian.competitionbackend.competition.repository.CompetitionRepository
import pl.adrian.competitionbackend.user.model.dto.UserDetailsImpl
import pl.adrian.competitionbackend.user.model.dto.UserDto
import pl.adrian.competitionbackend.user.model.dto.UserInfo
import pl.adrian.competitionbackend.user.model.entity.User
import pl.adrian.competitionbackend.user.service.UserService

@Service
class CompetitionService(private val competitionRepository: CompetitionRepository,
                         private val userService: UserService) {

    fun createCompetition(createCompetitionDto: CreateCompetitionDto, user: UserDetailsImpl) : Competition {
        val participants: MutableList<User> = userService.getUsersByUsername(createCompetitionDto.usernames.map { it.username }).toMutableList()
        val createdBy: User = userService.getUser(user.id.toString())
        participants.add(createdBy);

        return competitionRepository.save(Competition.toCompetition(createCompetitionDto, participants.map { UserInfo.fromUser(it) }.toList()))
    }
}