package com.example.coagusearch.modules.config


import com.example.coagusearch.shared.logging.ApiLogger
import com.example.coagusearch.modules.users.service.UserService
import org.hibernate.validator.HibernateValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

@Configuration
class WebMvcConfig @Autowired constructor(
        private val userService: UserService
       // private val userVersionService: UserVersionService
) : WebMvcConfigurer {
    companion object {
        const val MAX_AGE_SECS: Long = 3600
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("classpath:/resources/")
            .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic())
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
            .maxAge(MAX_AGE_SECS)
    }

    @Bean
    fun methodValidationPostProcessor(): MethodValidationPostProcessor =
        MethodValidationPostProcessor()
            .apply { setValidator(validator()) }

    @Bean
    fun validator(): LocalValidatorFactoryBean =
        LocalValidatorFactoryBean()
            .apply {
                setProviderClass(HibernateValidator::class.java)
                afterPropertiesSet()
            }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val stringConverter = StringHttpMessageConverter(Charset.forName("UTF-8"))
        stringConverter.supportedMediaTypes = listOf(
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_HTML,
            MediaType.APPLICATION_JSON
        )
        converters.add(stringConverter)
    }

    @Bean
    fun localeResolver(): LocaleResolver =
        CustomLocaleResolver()

    override fun addInterceptors(registry: InterceptorRegistry) {


    }
}
