package com.example.coagusearch.modules.patientData.response

import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.DoctorBloodOrderResponse

data class UserBloodTestDataResponse(
        val bloodTestId: Long,
        val userBloodData: List<UserBloodTestDataSpesificResponse>,
        val ordersOfData : List<DoctorBloodOrderResponse>
)

data class UserBloodTestDataSpesificResponse(
        val testName: String,
        val categoryList: List<UserBloodTestDataCategoryResponse>

)

data class UserBloodTestDataCategoryResponse(
        val categoryName: String,
        val maximumValue: Double,
        val minimumValue: Double,
        val optimalMaximumValue: Double,
        val optimalMinimumValue: Double,
        val value: Double
)
