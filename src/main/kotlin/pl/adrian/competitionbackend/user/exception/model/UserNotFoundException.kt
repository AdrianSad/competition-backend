package pl.adrian.competitionbackend.user.exception.model

import org.springframework.http.HttpStatus
import pl.adrian.competitionbackend.exception.model.DomainException

class UserNotFoundException : DomainException(status = HttpStatus.NOT_FOUND, code = "USER_NOT_FOUND", message = "User not found") {
}