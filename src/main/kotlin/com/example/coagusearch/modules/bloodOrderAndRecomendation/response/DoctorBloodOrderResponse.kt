package com.example.coagusearch.modules.bloodOrderAndRecomendation.response

data class DoctorBloodOrderResponse (
        val bloodType: String? = null,
        val rhType: String? = null,
        val productType: String? = null,
        val quantity: Double,
        val additionalNote: String?,
        val patientName: String? = null,
        val patientSurname : String? = null,
        val bloodTestId:Long?,
        val kind: String?,
        val unit: String?
)