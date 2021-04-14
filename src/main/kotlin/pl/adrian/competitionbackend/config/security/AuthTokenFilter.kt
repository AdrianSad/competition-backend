package pl.adrian.competitionbackend.config.security

import pl.adrian.competitionbackend.user.service.UserDetailsServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthTokenFilter(private val jwtUtils: JwtUtils, private val userService: UserDetailsServiceImpl) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(AuthTokenFilter::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        try {
            val jwt = parseJwt(httpServletRequest)
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                val username = jwtUtils.getUserNameJwtToken(jwt)
                val userDetails: UserDetails = userService.loadUserByUsername(username)
                val authenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                )
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        } catch (e: Exception) {
            log.error("Cannot set user authentication: {}", e)
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }
}