package pl.adrian.competitionbackend.user.model.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreateUserDto(

        @field:NotEmpty
        @field:Email
        val email: String = "",

        @field:NotEmpty
        @field:Size(min = 5, max = 20)
        @field:Pattern(regexp = "^[0-9A-Za-z]+$", message = "Nazwa użytkownika nie powinna zawierać znaków specjalnych")
        val username: String = "",

        @field:NotEmpty
        @field:Size(min = 5, max = 100)
        val password: String = "")