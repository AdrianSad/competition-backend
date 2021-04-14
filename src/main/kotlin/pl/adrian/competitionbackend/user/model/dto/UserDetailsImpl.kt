package pl.adrian.competitionbackend.user.model.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import pl.adrian.competitionbackend.user.model.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserDetailsImpl(var id: String?,
                      @get:JvmName("getUsername_") var username: String,
                      var email: String,
                      @JsonIgnore
                      @get:JvmName("getPassword_") var password: String,
                      @get:JvmName("getAuthorities_") var authorities: Collection<GrantedAuthority?>?) : UserDetails {

    companion object {
        fun build(user: User): UserDetailsImpl {
            val authorities: List<GrantedAuthority> = user.roles.stream()
                    .map { role -> SimpleGrantedAuthority(role.role.name) }
                    .collect(Collectors.toList())
            return UserDetailsImpl(user.id, user.username, user.email, user.password, authorities)
        }
    }

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}