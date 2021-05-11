package pl.adrian.competitionbackend.competition.model.dto

import javax.validation.constraints.NotEmpty

class Username(
        @field:NotEmpty
        var username: String = ""
)