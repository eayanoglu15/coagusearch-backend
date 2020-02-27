package com.example.coagusearch.modules.config

import com.example.coagusearch.security.UserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc
import java.util.*

@Configuration
@EnableSwagger2WebMvc
@Import(value = [SpringDataRestConfiguration::class])
class SpringFoxConfig {

    @Bean
    fun apiDocket(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .ignoredParameterTypes(UserPrincipal::class.java, Locale::class.java)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.coagusearch.modules"))
            .paths(PathSelectors.any())
            .build()
}
