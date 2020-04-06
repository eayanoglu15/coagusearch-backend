package com.example.coagusearch.modules.patientData.response

import com.example.coagusearch.shared.ApiResponse
import java.time.YearMonth

data class PatientDataResponse (
    val lastDataAnalysisTime: LastDataAnalysisTimeResponse = LastDataAnalysisTimeResponse(),
    val oldAnalysis : List<ApiResponse> = listOf()
)

data class LastDataAnalysisTimeResponse (
        val day: Int = 21,
        val month: Int = 3,
        val year: Int = 2020
)
