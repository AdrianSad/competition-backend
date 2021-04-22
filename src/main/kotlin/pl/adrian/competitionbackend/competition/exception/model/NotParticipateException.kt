package pl.adrian.competitionbackend.competition.exception.model

import org.springframework.http.HttpStatus
import pl.adrian.competitionbackend.exception.model.DomainException

class NotParticipateException : DomainException(status = HttpStatus.BAD_REQUEST, code = "USER_NOT_PARTICIPATE", message = "User not participate a competition")
