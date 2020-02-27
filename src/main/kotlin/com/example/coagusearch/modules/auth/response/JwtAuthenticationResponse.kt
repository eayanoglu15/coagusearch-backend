package com.example.coagusearch.modules.auth.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Success response for an jwt authentication request")
data class JwtAuthenticationResponse(
    @ApiModelProperty(value = "Jwt access token given to the user", name = "accessToken", required = true, position = 0)
    val accessToken: String,

    @ApiModelProperty(
        value = "Token type, always Bearer",
        name = "tokenType",
        required = true,
        example = "Bearer",
        position = 1
    )
    val tokenType: String = "Bearer",

    @ApiModelProperty(
        value = "Long lived refresh token for the user",
        name = "refreshToken",
        required = true,
        position = 2
    )
    val refreshToken: String
)
