package pl.adrian.competitionbackend.user.model.dto

import pl.adrian.competitionbackend.competition.model.entity.Day
import pl.adrian.competitionbackend.user.model.entity.User
import java.time.LocalDate
import java.util.*

data class UserInfo(
        val id: String?,
        val username: String,
        var statistics: NavigableMap<LocalDate, Day> = TreeMap(),
        var predictionData: NavigableMap<LocalDate, Day> = TreeMap(),
        var participatedDays: Int = 0,
        var totalSum: Int = 0,
        var averageScore: Double = 0.0,
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