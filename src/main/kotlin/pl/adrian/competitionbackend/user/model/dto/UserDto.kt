package pl.adrian.competitionbackend.user.model.dto

import pl.adrian.competitionbackend.user.model.entity.User
import kotlin.streams.toList

data class UserDto(
        val id: String?,
        val email: String,
        val username: String,
        val roles: List<String>,
        val token: String?
) {
    companion object {
        fun fromUser(user: User) = UserDto(
                id = user.id,
                email = user.email,
                username = user.username,
                roles = user.roles.stream().map { userRole -> userRole.role.name }.toList(),
                token = null
        )
    }
}