package com.example.coagusearch.modules.auth.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ApiModel(description = "Login Request object")
data class LoginRequest(
    @field:NotBlank
    @field:Size(max = 100)
    @ApiModelProperty(
        value = "identity number of the given User",
        name = "identity_number",
        required = true,
        example = "14051266621",
        position = 0,
        allowableValues = "range(0, 100]"
    )
    val identity_number: String,
    @field:NotBlank
    @ApiModelProperty(
        value = "Password of the given User",
        name = "password",
        required = true,
        example = "123456",
        position = 1,
        allowableValues = "range[6, infinity)"
    )
    val password: String
)
