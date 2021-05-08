package pl.adrian.competitionbackend.competition.model.dto

import pl.adrian.competitionbackend.competition.model.entity.Competition
import pl.adrian.competitionbackend.user.model.dto.UserInfo
import java.time.LocalDate
import java.util.*
import kotlin.streams.toList

class CompetitionDto(
        val id: String? = null,
        val title: String = "",
        val description: String = "",
        val category: String = "",
        val image: String = "",
        val addedById: String = "",
        val bestAverageUserId: String = "",
        val bestAverage: Double = 0.0,
        val endDate: LocalDate? = null,
        val usernames: List<UserInfo> = Collections.emptyList(),
        val labels: Set<LocalDate> = Collections.emptySet(),
        val labelsWholeCompetition: Set<LocalDate> = Collections.emptySet(),
) {
    companion object {
        fun toCompetitionDto(competition: Competition) = CompetitionDto(
                id = competition.id,
                title = competition.title,
                description = competition.description,
                category = competition.category,
                image = competition.image,
                addedById = competition.addedById,
                bestAverageUserId = competition.bestAverageUserId,
                bestAverage = competition.bestAverage,
                endDate = competition.endDate,
                usernames = competition.users
        )

        fun toCompetitionDtoWithLabels(competition: Competition, labels: Set<LocalDate>, labelsWholeCompetition: Set<LocalDate>) = CompetitionDto(
                id = competition.id,
                title = competition.title,
                description = competition.description,
                category = competition.category,
                image = competition.image,
                addedById = competition.addedById,
                usernames = competition.users,
                bestAverageUserId = competition.bestAverageUserId,
                bestAverage = competition.bestAverage,
                endDate = competition.endDate,
                labels = labels,
                labelsWholeCompetition = labelsWholeCompetition,
        )
    }
}