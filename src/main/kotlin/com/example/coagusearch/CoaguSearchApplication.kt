package com.example.coagusearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class CoaguSearchApplication

fun main(args: Array<String>) {
    runApplication<CoaguSearchApplication>(*args)
}
