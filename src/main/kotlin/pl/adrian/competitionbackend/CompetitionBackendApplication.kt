package pl.adrian.competitionbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CompetitionBackendApplication

fun main(args: Array<String>) {
    runApplication<CompetitionBackendApplication>(*args)
}
