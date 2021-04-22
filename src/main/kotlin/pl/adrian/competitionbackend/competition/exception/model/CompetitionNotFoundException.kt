package pl.adrian.competitionbackend.competition.exception.model

import org.springframework.http.HttpStatus
import pl.adrian.competitionbackend.exception.model.DomainException

class CompetitionNotFoundException : DomainException(status = HttpStatus.NOT_FOUND, code = "COMPETITION_NOT_FOUND", message = "Competition not found")