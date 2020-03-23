package com.example.coagusearch.modules.users.response

import com.example.coagusearch.modules.appointment.response.SingleAppointmentResponse

data class PatientMainScreen (
  var patientMissingInfo : Boolean = false,
  var patientNextAppointment : SingleAppointmentResponse?
)
