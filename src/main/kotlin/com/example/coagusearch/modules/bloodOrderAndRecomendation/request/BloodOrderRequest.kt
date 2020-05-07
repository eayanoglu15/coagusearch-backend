package com.example.coagusearch.modules.bloodOrderAndRecomendation.request

data class BloodOrderRequest (
        val bloodType: String,
        val rhType: String,
        val patientId: Long?,
        val productType: String,
        val unit: Double,
        val additionalNote: String?
)
data class SetReadyBloodOrderRequest (
        val bloodOrderId: Long
)