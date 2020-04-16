package com.example.coagusearch.modules.bloodOrderAndRecomendation.response

data class DoctorBloodOrderResponse (
        val bloodType: String? = null,
        val rhType: String? = null,
        val productType: String? = null,
        val unit: Int,
        val additionalNote: String?,
        val patientName: String? = null,
        val patientSurname : String? = null
)