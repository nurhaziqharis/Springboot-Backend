package com.brobarber.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
class BroBarberApplication

fun main(args: Array<String>) {
    runApplication<BroBarberApplication>(*args)
}
