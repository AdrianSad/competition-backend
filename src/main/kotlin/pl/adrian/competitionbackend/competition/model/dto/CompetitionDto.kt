package pl.adrian.competitionbackend.competition.model.dto

import pl.adrian.competitionbackend.competition.model.entity.Competition
import pl.adrian.competitionbackend.user.model.dto.UserInfo
import java.util.*
import kotlin.streams.toList

class CompetitionDto(
        val id: String? = null,
        val title: String = "",
        val description: String = "",
        val category: String = "",
        val image: String = "",
        val addedById: String = "",
        val usernames: List<UserInfo> = Collections.emptyList()
) {
    companion object {
        fun toCompetitionDto(competition: Competition) = CompetitionDto(
                id = competition.id,
                title = competition.title,
                description = competition.description,
                category = competition.category,
                image = competition.image,
                addedById = competition.addedById,
                usernames = competition.users
        )
    }
}