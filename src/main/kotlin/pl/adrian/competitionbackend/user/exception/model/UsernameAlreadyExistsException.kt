package pl.adrian.competitionbackend.user.exception.model

import pl.adrian.competitionbackend.exception.model.DomainException
import org.springframework.http.HttpStatus

class UsernameAlreadyExistsException : DomainException(status = HttpStatus.BAD_REQUEST, code = "USERNAME_ALREADY_EXISTS", message = "Username already exists")