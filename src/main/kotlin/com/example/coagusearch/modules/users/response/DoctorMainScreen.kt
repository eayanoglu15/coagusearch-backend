package com.example.coagusearch.modules.users.response

import kotlin.random.Random

data class DoctorMainScreen(
        val emergencyPatients: List<EmergencyPatientDetail>,
        val todayAppointments: List<TodayPatientDetail>


)

data class TodayPatientDetail(
        val patientId: Long,
        val userName: String,
        val userSurname: String,
        val appointmentHour: PatientAppointmentTimeResponse


)

data class PatientAppointmentTimeResponse(
        val hour: Int,
        val minute: Int
)

data class EmergencyPatientDetail(
        val patientId: Long,
        val userName: String,
        val userSurname: String,
        val arrivalHour: PatientAppointmentTimeResponse,
        val isUserAtAmbulance: Boolean,
        val isDataReady: Boolean

)
