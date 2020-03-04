package com.example.coagusearch.modules.auth.response
import io.swagger.annotations.ApiModel

@ApiModel(description = "Response given to an user save request")
data class UserSaveResponse(
        var protocolCode: String?
)
