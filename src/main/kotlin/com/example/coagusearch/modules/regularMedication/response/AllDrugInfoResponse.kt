package com.example.coagusearch.modules.regularMedication.response

data class AllDrugInfoResponse(
        val drugs: List<GetDrugRequest>,
        val frequencies: List<GetFrequencyRequest>
)

data class GetDrugRequest(
        val key: String,
        val content: String
)

data class GetFrequencyRequest(
        val key: String,
        val title: String
)
