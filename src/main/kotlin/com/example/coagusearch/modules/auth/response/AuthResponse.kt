package com.example.coagusearch.modules.auth.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.context.MessageSource
import java.util.Locale

@ApiModel(description = "Response given to an auth request")
data class AuthResponse(
    @ApiModelProperty(
        value = "Whether the auth request was successful",
        name = "success",
        required = true,
        example = "true",
        position = 0
    )
    val success: Boolean,

    @ApiModelProperty(
        value = "Auth message",
        name = "message",
        required = true,
        example = "User authenticated successfully.",
        position = 1
    )
    val message: String
) {
    companion object {
        fun fromMessage(
            messageSource: MessageSource,
            locale: Locale,
            success: Boolean,
            key: String,
            vararg args: Any
        ): AuthResponse {
            val messageString = messageSource.getMessage(key, args, locale)
            return AuthResponse(success, messageString)
        }
    }
}
