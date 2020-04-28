package com.example.coagusearch.modules.patientData.response

data class SuggestionListResponse (
        val userSuggestionList : List<SuggestionResponse>

)

data class SuggestionResponse (
        val diagnosis : String? = null,
        val unit: String,
        val kind: String,
        val quantity: Double,
        val product:String
)