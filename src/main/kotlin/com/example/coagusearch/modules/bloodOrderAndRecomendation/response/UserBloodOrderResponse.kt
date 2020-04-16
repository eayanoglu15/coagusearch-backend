package com.example.coagusearch.modules.bloodOrderAndRecomendation.response


data class UserBloodOrderResponse (
        val bloodType: String? = null,
        val rhType: String? = null,
        val productType: String? = null,
        val unit: Int,
        val additionalNote: String?
)