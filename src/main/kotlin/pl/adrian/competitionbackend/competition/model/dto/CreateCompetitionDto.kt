package pl.adrian.competitionbackend.competition.model.dto

import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

class CreateCompetitionDto(

        @NotEmpty
        val title: String = "",

        @NotEmpty
        val description: String = "",

        @NotEmpty
        val category: String = "",

        @NotEmpty
        val image: String = "",

        @NotEmpty
        @Valid
        val usernames: List<Username> = Collections.emptyList()
)