package pl.adrian.competitionbackend.competition.exception.model

import org.springframework.http.HttpStatus
import pl.adrian.competitionbackend.exception.model.DomainException

class AlreadyParticipateException : DomainException(status = HttpStatus.BAD_REQUEST, code = "USER_ALREADY_PARTICIPATE", message = "User already participate a competition")