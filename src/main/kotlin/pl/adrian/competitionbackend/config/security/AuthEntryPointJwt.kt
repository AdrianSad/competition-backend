package pl.adrian.competitionbackend.config.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(AuthEntryPointJwt::class.java)

    override fun commence(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse?, e: AuthenticationException?) {
        logger.error("Unauthorized error: {}", e!!.message)
        httpServletResponse!!.contentType = "application/json"
        httpServletResponse.status = HttpServletResponse.SC_UNAUTHORIZED
        httpServletResponse.outputStream.println("{ \"error\": \"" + e.message + "\" }")
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
    }
}