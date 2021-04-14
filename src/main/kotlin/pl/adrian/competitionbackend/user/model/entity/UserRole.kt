package pl.adrian.competitionbackend.user.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.adrian.competitionbackend.user.model.entity.ERole

@Document
data class UserRole(

        @Id
        val id: String,
        val role: ERole
)