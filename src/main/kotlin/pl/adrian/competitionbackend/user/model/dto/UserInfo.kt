package pl.adrian.competitionbackend.user.model.dto

data class UserInfo(
        val id: String?,
        val username: String,
) {
    companion object {
        fun fromUserDetails(userDetailsImpl: UserDetailsImpl) = UserInfo(
                id = userDetailsImpl.id,
                username = userDetailsImpl.username,
        )
    }
}