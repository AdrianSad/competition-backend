package pl.adrian.competitionbackend.competition.exception.model

import org.springframework.http.HttpStatus
import pl.adrian.competitionbackend.exception.model.DomainException

class CompetitionEndedException : DomainException(status = HttpStatus.BAD_REQUEST, code = "COMPETITION_ENDED", message = "Competition already ended")