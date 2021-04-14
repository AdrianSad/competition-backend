package pl.adrian.competitionbackend.user.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.HashSet

@Document
data class User(

        @Id
        var id: String? = null,
        var email: String = "",
        var username: String = "",
        var password: String = "",
        var roles: Set<UserRole> = HashSet<UserRole>()
)