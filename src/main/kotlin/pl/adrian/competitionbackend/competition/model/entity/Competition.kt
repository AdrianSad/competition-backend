package pl.adrian.competitionbackend.competition.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.adrian.competitionbackend.competition.model.dto.CreateCompetitionDto
import pl.adrian.competitionbackend.user.model.dto.UserDto
import pl.adrian.competitionbackend.user.model.dto.UserInfo
import java.time.LocalDate
import java.util.*

@Document
data class Competition(
        @Id
        var id: String? = null,
        var title: String = "",
        var description: String = "",
        var category: String = "",
        var image: String = "",
        var addedById: String = "",
        var bestAverageUserId: String = "",
        var bestAverage: Double = 0.0,
        val endDate: LocalDate? = null,
        var users: List<UserInfo> = Collections.emptyList()
) {
    companion object {
        fun toCompetition(createCompetition: CreateCompetitionDto, users: List<UserInfo>, addedById: String) = Competition(
                title = createCompetition.title,
                description = createCompetition.description,
                category = createCompetition.category,
                image = createCompetition.image,
                endDate = createCompetition.endDate,
                addedById = addedById,
                users = users
        )
    }
}
