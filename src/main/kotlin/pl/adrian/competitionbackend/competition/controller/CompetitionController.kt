package pl.adrian.competitionbackend.competition.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import pl.adrian.competitionbackend.competition.model.dto.CompetitionDto
import pl.adrian.competitionbackend.competition.model.dto.CompetitionDtos
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

    @GetMapping
    fun getUserCompetition(@AuthenticationPrincipal userDetailsImpl: UserDetailsImpl): List<CompetitionDto> {
        val competitions = competitionService.getUserCompetitions(userDetailsImpl);
        return CompetitionDtos.toCompetitionDtos(competitions);
    }

    @GetMapping("/{id}")
    fun getCompetition(@PathVariable id: String): CompetitionDto =
            CompetitionDto.toCompetitionDto(competitionService.getCompetition(id));

    @PostMapping("/leave/{id}")
    fun leaveCompetition(@PathVariable id: String,
                         @AuthenticationPrincipal userDetailsImpl: UserDetailsImpl): CompetitionDto =
            CompetitionDto.toCompetitionDto(competitionService.leaveCompetition(userDetailsImpl, id));

    @PostMapping("/attend/{id}")
    fun attendCompetition(@PathVariable id: String,
                         @AuthenticationPrincipal userDetailsImpl: UserDetailsImpl): CompetitionDto =
            CompetitionDto.toCompetitionDto(competitionService.attendCompetition(userDetailsImpl, id));
}