package pl.adrian.competitionbackend.competition.model.dto

import pl.adrian.competitionbackend.competition.model.entity.Competition

class CompetitionDtos (
        val competitions: List<CompetitionDto>
        ) {
    companion object {
        fun toCompetitionDtos(competitions: List<Competition>) =
                competitions.map { CompetitionDto.toCompetitionDto(it) }.toList()
    }
}