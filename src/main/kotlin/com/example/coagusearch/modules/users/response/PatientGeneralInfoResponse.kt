package com.example.coagusearch.modules.users.response

import kotlin.random.Random

data class PatientGeneralInfoResponse(
        val userId: Long,
        var name: String?,
        var surname: String?,
        var isNewData: Boolean = Random.nextBoolean()
)