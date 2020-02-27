package com.example.coagusearch.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.util.Locale
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint @Autowired constructor(
    private val messageSource: MessageSource
) : AuthenticationEntryPoint {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)
        const val NOT_AUTHORIZED_MESSAGE = "Exception.notAuthorized"
    }

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        logger.warn(
            "Responding with unauthorized error ${request?.contextPath ?: ""}. Message - ${authException?.message
                ?: "No Message"}. Path: ${request?.requestURI}"
        )
        val locale = request?.locale ?: Locale.ENGLISH
        val message = messageSource.getMessage(NOT_AUTHORIZED_MESSAGE, null, locale)
        response?.sendError(
            HttpServletResponse.SC_UNAUTHORIZED,
            message
        )
    }
}
