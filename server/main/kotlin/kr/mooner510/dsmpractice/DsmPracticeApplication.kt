package kr.mooner510.dsmpractice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class DsmPracticeApplication

fun main(args: Array<String>) {
    runApplication<DsmPracticeApplication>(*args)
}
