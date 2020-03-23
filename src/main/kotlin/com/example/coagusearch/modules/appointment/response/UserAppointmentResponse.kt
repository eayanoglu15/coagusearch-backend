package com.example.coagusearch.modules.appointment.response

import com.example.coagusearch.modules.base.model.KeyType

data class UserAppointmentResponse(
        var nextAppointment: SingleAppointmentResponse? = null,
        var oldAppointment: List<SingleAppointmentResponse>
)

data class SingleAppointmentResponse(
        var id: KeyType,
        var doctorName: String?,
        var doctorSurname: String?,
        var day: Int,
        var month: Int,
        var year: Int,
        var hour: Int,
        var minute: Int
)
