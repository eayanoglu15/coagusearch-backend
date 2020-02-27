package com.example.coagusearch.modules.users.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Response given to an user request")
data class UserResponse(
        val identityNumber: String,
        val type: String,
        val userId: Long,
        var name: String?,
        var surname: String?,
        var dateOfBirth: String?,
        var height: Double?,
        var weight: Double?,
        var bloodType: String?,
        var rhType: String?,
        var gender: String?
)