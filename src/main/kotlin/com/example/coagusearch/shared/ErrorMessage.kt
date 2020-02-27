package com.example.coagusearch.shared

sealed class ErrorMessage {
    companion object {
        fun from(message: String, exception: Throwable? = null) =
            SingleErrorMessage(false, message, exception?.toString())

        fun from(message: String, messages: List<String>, exception: Throwable? = null) =
            ErrorMessages(false, message, exception?.toString(), messages)
    }
}

open class SingleErrorMessage(
    val success: Boolean,
    val message: String,
    val exceptionMessage: String?
) : ErrorMessage()

class ErrorMessages(
    success: Boolean,
    message: String,
    exceptionMessage: String?,
    val messages: List<String>
) : SingleErrorMessage(success, message, exceptionMessage)
