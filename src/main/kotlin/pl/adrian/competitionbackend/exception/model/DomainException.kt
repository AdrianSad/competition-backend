package pl.adrian.competitionbackend.exception.model

import org.springframework.http.HttpStatus

open class DomainException(val code: String,
                           val status: HttpStatus,
                           override val message: String) : RuntimeException()