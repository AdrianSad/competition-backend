package pl.adrian.competitionbackend.user.service

import pl.adrian.competitionbackend.user.model.dto.UserDetailsImpl
import pl.adrian.competitionbackend.user.model.entity.User
import pl.adrian.competitionbackend.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User Not Found with username: $username")
        return UserDetailsImpl.build(user)
    }
}