package com.example.coagusearch.modules.users.response

import com.example.coagusearch.modules.appointment.response.UserAppointmentResponse
import com.example.coagusearch.modules.patientData.response.PatientDataResponse

data class PatientDetailScreen(
        val patientResponse: UserResponse,
        val userAppointmentResponse: UserAppointmentResponse,
        val userDataResponse: PatientDataResponse
)
