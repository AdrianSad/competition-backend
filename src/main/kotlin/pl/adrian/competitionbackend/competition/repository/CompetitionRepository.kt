package pl.adrian.competitionbackend.competition.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.adrian.competitionbackend.competition.model.entity.Competition
import pl.adrian.competitionbackend.user.model.dto.UserInfo

@Repository
interface CompetitionRepository : MongoRepository<Competition, String> {

    fun findAllByUsersContains(user: UserInfo) : List<Competition>
}