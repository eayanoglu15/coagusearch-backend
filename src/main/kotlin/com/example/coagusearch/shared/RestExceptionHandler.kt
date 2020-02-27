package com.example.coagusearch.shared

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.Locale
import kotlin.streams.toList

@ControllerAdvice
class RestExceptionHandler @Autowired constructor(
    private val messageSource: MessageSource
) {

    @ExceptionHandler(RestException::class)
    fun handleIllegalArgument(ex: RestException, locale: Locale): ResponseEntity<ErrorMessage> {
        val errorMessage = messageSource.getMessage(ex.message, ex.args, locale)
        // val message = "Illegal argument occurred: " + (ex.hiddenMessage ?: "") + "\n" +
        //        "Sent message: " + errorMessage
        // logger.error(message, ex)
        return ResponseEntity(ErrorMessage.from(errorMessage, ex), ex.statusCode)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        locale: Locale
    ): ResponseEntity<ErrorMessage> {
        val result = ex.bindingResult
        val errorMessages = result.allErrors
            .stream()
            .map {
                try {
                    val field = (it as? FieldError)?.field ?: ""
                    val message = messageSource.getMessage(it, locale)
                    "${field.capitalize()} $message"
                } catch (_: NoSuchMessageException) {
                    logger.error("Missing message: $it")
                    "${it.objectName}: ${it.code}"
                }
            }
            .toList()
        val firstMessage = errorMessages.getOrElse(0) { messageSource.getMessage(UNEXPECTED_MESSAGE, null, locale) }
        logger.error("Argument not valid occurred", ex)
        return ResponseEntity(
            ErrorMessage.from(
                firstMessage,
                errorMessages,
                ex
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFound(ex: UsernameNotFoundException, locale: Locale): ResponseEntity<ErrorMessage> {
        val errorMessage = messageSource.getMessage(USERNAME_OR_PASSWORD_INVALID, null, locale)
        // logger.error("Username not found exception occurred", ex)
        return ResponseEntity(ErrorMessage.from(errorMessage, ex), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException, locale: Locale): ResponseEntity<ErrorMessage> {
        val errorMessage = messageSource.getMessage(USERNAME_OR_PASSWORD_INVALID, null, locale)
        // logger.error("Bad credentials exception occurred", ex)
        return ResponseEntity(ErrorMessage.from(errorMessage, ex), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        locale: Locale
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = messageSource.getMessage(INPUT_JSON_IS_WRONG, null, locale)
        /*val result = BufferedReader(InputStreamReader(ex.httpInputMessage.body)).lines()
            .asSequence()
            .joinToString("\n")
        */
        // logger.error("Given input is not correct, the message was: $result", ex)
        return ResponseEntity(ErrorMessage.from(errorMessage, ex), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception, locale: Locale): ResponseEntity<ErrorMessage> {
        val errorMessage = messageSource.getMessage(UNEXPECTED_MESSAGE, null, locale)
        logger.error("Unexpected exception occurred", ex)
        return ResponseEntity(ErrorMessage.from(errorMessage, ex), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object {
        const val UNEXPECTED_MESSAGE = "Exception.unexpected"
        const val USERNAME_OR_PASSWORD_INVALID = "Exception.usernameOrPasswordInvalid"
        const val NOT_FOUND_MESSAGE = "Exception.notFound"
        const val INPUT_JSON_IS_WRONG = "Exception.inputJsonIsWrong"
        val logger = LoggerFactory.getLogger(RestExceptionHandler::class.java)!!
    }
}
