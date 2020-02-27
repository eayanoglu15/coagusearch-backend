package com.example.coagusearch.shared

import org.springframework.http.HttpStatus

class RestException(
        override val message: String,
        val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
        vararg val args: Any
) : RuntimeException(message) {
    var hiddenMessage: String? = null

    override fun toString(): String {
        return "RestException ($statusCode): $message\n$hiddenMessage\n${args.joinToString(",")}"
    }
}
