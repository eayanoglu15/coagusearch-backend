package com.example.coagusearch.modules.users.response

import com.example.coagusearch.modules.appointment.response.UserAppointmentResponse
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.UserBloodOrderResponse
import com.example.coagusearch.modules.patientData.response.BloodDateResponse
import com.example.coagusearch.modules.patientData.response.PatientDataResponse
import com.example.coagusearch.modules.patientData.response.UserBloodTestHistoryResponse
import com.example.coagusearch.modules.regularMedication.response.MedicineInfoResponse

data class PatientDetailScreen(
        val patientResponse: UserResponse,
        val userAppointmentResponse: UserAppointmentResponse,
        val lastDataAnalysisTime: UserBloodTestHistoryResponse? = null,
        val patientDrugs: List<MedicineInfoResponse>,
        val previousBloodOrders: List<UserBloodOrderResponse>
)
