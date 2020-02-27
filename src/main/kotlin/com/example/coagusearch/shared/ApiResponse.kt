package com.example.coagusearch.shared

import org.springframework.context.MessageSource
import java.util.Locale

data class ApiResponse(
        val success: Boolean,
        val message: String
) {
    companion object {
        fun fromMessage(
                messageSource: MessageSource,
                locale: Locale,
                success: Boolean,
                key: String,
                vararg args: Any
        ): ApiResponse {
            val messageString = messageSource.getMessage(key, args, locale)
            return ApiResponse(success, messageString)
        }
    }
}
