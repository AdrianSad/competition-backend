package pl.adrian.competitionbackend.user.repository

import pl.adrian.competitionbackend.user.model.entity.ERole
import pl.adrian.competitionbackend.user.model.entity.UserRole
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : MongoRepository<UserRole, String> {
    fun findByRole(name: ERole): UserRole?
}