package pl.adrian.competitionbackend.config.security

import io.jsonwebtoken.*
import pl.adrian.competitionbackend.user.model.dto.UserDetailsImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(@Value("\${competition.jwtSecret}")
               private val jwtSecret: String,
               @Value("\${competition.jwtExpirationMs}")
               private val jwtExpirationMs: String) {

    private val logger = LoggerFactory.getLogger(AuthEntryPointJwt::class.java)

    fun generateJwtToken(authentication: Authentication): String? {
        val userDetailsPrincipal: UserDetailsImpl = authentication.principal as UserDetailsImpl

        return Jwts.builder()
                .setSubject((userDetailsPrincipal.username))
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + jwtExpirationMs.toLong()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    fun getUserNameJwtToken(jwtToken: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).body.subject
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT string is empty: {}", e.message)
        }

        return false
    }
}