package pl.adrian.competitionbackend.user.service

import pl.adrian.competitionbackend.config.security.JwtUtils
import pl.adrian.competitionbackend.user.exception.model.EmailAlreadyExistsException
import pl.adrian.competitionbackend.user.exception.model.RoleNotFoundException
import pl.adrian.competitionbackend.user.exception.model.UsernameAlreadyExistsException
import pl.adrian.competitionbackend.user.model.dto.CreateUserDto
import pl.adrian.competitionbackend.user.model.dto.LoginUserDto
import pl.adrian.competitionbackend.user.model.dto.UserDetailsImpl
import pl.adrian.competitionbackend.user.model.dto.UserDto
import pl.adrian.competitionbackend.user.model.entity.ERole
import pl.adrian.competitionbackend.user.model.entity.User
import pl.adrian.competitionbackend.user.model.entity.UserRole
import pl.adrian.competitionbackend.user.repository.RoleRepository
import pl.adrian.competitionbackend.user.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class UserService(private val userRepository: UserRepository,
                  private val roleRepository: RoleRepository,
                  private val authenticationManager: AuthenticationManager,
                  private val jwtUtils: JwtUtils,
                  private val passwordEncoder: PasswordEncoder) {

    fun signIn(loginUserDto: LoginUserDto): UserDto {
        val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginUserDto.username, loginUserDto.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String? = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities!!.stream().map { authority -> authority!!.authority }.collect(Collectors.toList())
        return UserDto(userDetails.id, userDetails.email, userDetails.username, roles, jwt!!)
    }

    fun signUp(createUserDto: CreateUserDto): User {
        if (userRepository.existsByEmail(createUserDto.email)) {
            throw EmailAlreadyExistsException()
        }
        if (userRepository.existsByUsername(createUserDto.username)) {
            throw UsernameAlreadyExistsException()
        }

        val role = roleRepository.findByRole(ERole.ROLE_USER) ?: throw RoleNotFoundException()
        val userRoles: Set<UserRole> = setOf(role)

        val user = User()
        user.email = createUserDto.email
        user.username = createUserDto.username
        user.password = passwordEncoder.encode(createUserDto.password)
        user.roles = userRoles
        return userRepository.save(user)
    }
}
