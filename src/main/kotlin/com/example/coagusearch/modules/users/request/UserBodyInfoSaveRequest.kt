package com.example.coagusearch.modules.users.request
import io.swagger.annotations.ApiModel

@ApiModel(description = "Response given to an user request")
data class UserBodyInfoSaveRequest(
        var name: String?,
        var surname: String?,
        var dateOfBirth: String?,
        var height: Double?,
        var weight: Double?,
        var bloodType: String?,
        var rhType: String?,
        var gender: String?
)