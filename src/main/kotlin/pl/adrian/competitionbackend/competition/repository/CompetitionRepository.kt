package pl.adrian.competitionbackend.competition.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.adrian.competitionbackend.competition.model.entity.Competition

@Repository
interface CompetitionRepository : MongoRepository<Competition, String> {
}