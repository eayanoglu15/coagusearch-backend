package com.example.coagusearch.modules.bloodOrderAndRecomendation.response

import java.time.LocalTime

data class WebBloodOrderResponse (
        val req_date: String? = null,
        val req_time: LocalTime? = null,
        val blood_type_name: String? = null,
        val units: Int? = null,
        val requester_name: String? = null,
        val isConfirmed: String? = null
)


