package com.example.coagusearch.modules.patientData.request

import com.example.coagusearch.modules.base.model.KeyType

data class GetPatientBloodTestRequest (
        val patientId: Long
)

data class GetPatientBloodTestDataRequest (
        val bloodTestDataId: Long
)