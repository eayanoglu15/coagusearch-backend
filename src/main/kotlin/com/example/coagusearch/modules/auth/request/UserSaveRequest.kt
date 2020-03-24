package com.example.coagusearch.modules.auth.request
import io.swagger.annotations.ApiModel

@ApiModel(description = "Response given to an user save request")
data class UserSaveRequest(
        val identity_number: String,
        val type: String,
        var name: String?,
        var surname: String?,
        var birthDay : Int?,
        var birthMonth : Int?,
        var birthYear: Int?,
        var height: Double?,
        var weight: Double?,
        var bloodType: String?,
        var rhType: String?,
        var gender: String?,
        var doctor_identity_number : String?
)
