package com.example.coagusearch.modules.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class JacksonConfiguration {

    @Bean
    fun objectMapper(): Jackson2ObjectMapperBuilder =
        Jackson2ObjectMapperBuilder().apply {
            serializationInclusion(JsonInclude.Include.NON_NULL)
            featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            modules(KotlinModule(), JavaTimeModule(), ParameterNamesModule(), Jdk8Module())
        }
}
