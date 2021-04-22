package pl.adrian.competitionbackend.competition.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.adrian.competitionbackend.competition.model.dto.CreateCompetitionDto
import pl.adrian.competitionbackend.competition.service.CompetitionService
import pl.adrian.competitionbackend.user.model.dto.UserDetailsImpl
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/competition")
class CompetitionController(private val competitionService: CompetitionService) {

    @PostMapping
    fun createCompetition(@RequestBody @Valid createCompetitionDto: CreateCompetitionDto,
                          @AuthenticationPrincipal userDetailsImpl: UserDetailsImpl) =
            competitionService.createCompetition(createCompetitionDto, userDetailsImpl);
}