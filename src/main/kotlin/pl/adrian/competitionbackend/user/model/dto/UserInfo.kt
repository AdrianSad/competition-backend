package pl.adrian.competitionbackend.user.model.dto

import pl.adrian.competitionbackend.user.model.entity.User
import java.time.LocalDate

data class UserInfo(
        val id: String?,
        val username: String,
        var statistics: HashMap<LocalDate, Int> = HashMap()
) {
    companion object {
        fun fromUserDetails(userDetailsImpl: UserDetailsImpl) = UserInfo(
                id = userDetailsImpl.id,
                username = userDetailsImpl.username,
        )

        fun fromUser(user: User) = UserInfo(
                id = user.id,
                username = user.username,
        )
    }
}