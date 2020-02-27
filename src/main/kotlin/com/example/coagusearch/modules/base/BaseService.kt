package com.example.coagusearch.modules.base

import com.example.coagusearch.shared.RestException
import com.example.coagusearch.shared.RestExceptionHandler
import org.springframework.http.HttpStatus
import java.util.Optional

abstract class BaseService

inline fun <T> Optional<out T>.requireExists(
    messageAndFieldExtractor: () -> Pair<String, String>
): T {
    return this.orElse(null).requireExists(messageAndFieldExtractor)
}

inline fun <T> T?.requireExists(
    messageAndFieldExtractor: () -> Pair<String, String>
): T {
    if (this != null) {
        return this
    }
    val error = messageAndFieldExtractor()
    throw RestException(
        RestExceptionHandler.NOT_FOUND_MESSAGE,
        HttpStatus.BAD_REQUEST,
        error.first,
        error.second
    )
}
