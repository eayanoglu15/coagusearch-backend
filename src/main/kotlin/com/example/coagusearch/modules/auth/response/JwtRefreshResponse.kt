package com.example.coagusearch.modules.auth.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Success response for an refresh request")
data class JwtRefreshResponse(
    @ApiModelProperty(value = "Jwt access token given to the user", name = "accessToken", required = true, position = 0)
    val accessToken: String,

    @ApiModelProperty(
        value = "Token type, always Bearer",
        name = "tokenType",
        required = true,
        example = "Bearer",
        position = 1
    )
    val tokenType: String = "Bearer"
)
