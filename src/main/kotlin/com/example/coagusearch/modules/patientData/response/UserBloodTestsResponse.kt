package com.example.coagusearch.modules.patientData.response


data class UserBloodTestsResponse(
         val userTestList: List<UserBloodTestHistoryResponse>
)

data class UserBloodTestHistoryResponse(
         val id: Long,
         val testDate: BloodDateResponse
)
data class BloodDateResponse(
         val day: Int,
         val month: Int,
         val year: Int
)
