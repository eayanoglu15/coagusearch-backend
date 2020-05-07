package com.example.coagusearch.modules.bloodOrderAndRecomendation.response

data class MedicalBloodOrderResponse (
        val todoOrderList : List<DoctorBloodOrderResponse>,
        val doneOrderList : List<DoctorBloodOrderResponse>
)