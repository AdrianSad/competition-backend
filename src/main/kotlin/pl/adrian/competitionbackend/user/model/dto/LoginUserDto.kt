package pl.adrian.competitionbackend.user.model.dto

import javax.validation.constraints.NotEmpty

data class LoginUserDto(

        @field:NotEmpty
        val username: String = "",

        @field:NotEmpty
        val password: String = ""
)