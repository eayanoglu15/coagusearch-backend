package com.example.coagusearch.modules.bloodOrderAndRecomendation.response


data class UserBloodOrderResponse (
        val bloodType: String? = null,
        val rhType: String? = null,
        val productType: String? = null,
        val quantity: Double,
        val additionalNote: String?,
        val bloodTestId:Long?,
        val kind: String?,
        val unit: String?,
        val isReady : Boolean,
        val bloodOrderId: Long
)