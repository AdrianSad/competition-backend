package pl.adrian.competitionbackend.user.exception.model

import pl.adrian.competitionbackend.exception.model.DomainException
import org.springframework.http.HttpStatus

class RoleNotFoundException : DomainException(status = HttpStatus.NOT_FOUND, code = "ROLE_NOT_FOUND", message = "Role not found")