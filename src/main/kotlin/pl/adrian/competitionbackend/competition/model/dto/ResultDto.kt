package pl.adrian.competitionbackend.competition.model.dto

import javax.validation.constraints.NotNull

data class ResultDto(
        @NotNull
        val result: Int
        )