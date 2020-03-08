package com.example.coagusearch.modules.appointment.request

data class SaveAppointmentsForUserRequest(
        val day: Int,
        val month: Int,
        val year: Int,
        val hour: Int,
        val minute: Int
)