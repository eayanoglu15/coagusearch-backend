package com.example.coagusearch.modules.auth.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

@ApiModel(description = "Refresh Request object")
data class RefreshRequest(
    @field:NotBlank
    @ApiModelProperty(
        value = "Refresh token assigned to the user",
        name = "refreshToken",
        required = true,
        position = 1
    )
    val refreshToken: String
)
