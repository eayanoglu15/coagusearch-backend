package com.example.coagusearch.modules.users.response

import com.example.coagusearch.modules.appointment.response.SingleAppointmentResponse
import com.example.coagusearch.modules.notification.response.NotificationResponse

data class PatientMainScreen(
        var patientMissingInfo: Boolean = false,
        var patientNextAppointment: SingleAppointmentResponse?,
        val patientNotifications: List<NotificationResponse>
)
