package pl.adrian.competitionbackend.competition.model.dto

import java.time.LocalDate
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateCompetitionDto(

        @field:NotEmpty
        val title: String = "",

        @field:NotEmpty
        val description: String = "",

        @field:NotEmpty
        val category: String = "",

        @field:NotEmpty
        val image: String = "",

        @field:NotNull
        val endDate: LocalDate? = null,

        @field:NotEmpty
        @field:Valid
        val usernames: List<Username> = Collections.emptyList()
)