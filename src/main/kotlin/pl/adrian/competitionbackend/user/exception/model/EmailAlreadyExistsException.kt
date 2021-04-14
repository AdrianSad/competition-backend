package pl.adrian.competitionbackend.user.exception.model

import pl.adrian.competitionbackend.exception.model.DomainException
import org.springframework.http.HttpStatus

class EmailAlreadyExistsException : DomainException(status = HttpStatus.BAD_REQUEST, code = "EMAIL_ALREADY_EXISTS", message = "Email already exists")