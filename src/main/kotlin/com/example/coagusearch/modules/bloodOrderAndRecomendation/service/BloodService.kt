package com.example.coagusearch.modules.bloodOrderAndRecomendation.service

import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.BloodOrder
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.BloodOrderRepository
import com.example.coagusearch.modules.bloodOrderAndRecomendation.request.BloodOrderRequest
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.BloodStatusResponse
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserBloodType
import com.example.coagusearch.modules.users.model.UserRhType
import com.example.coagusearch.modules.users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BloodService @Autowired constructor(
        private val bloodOrderRepository: BloodOrderRepository,
        private val userService: UserService
) {
    fun handleOrder(doctor: User,
                    language: Language,
                    bloodOrderRequest: BloodOrderRequest
    ): BloodStatusResponse {
        val patient = if (bloodOrderRequest.patientId != null)
            userService.getUserById(bloodOrderRequest.patientId)
        else null
        bloodOrderRepository.save(
                BloodOrder(
                        doctor = doctor,
                        patient = patient,
                        bloodType = UserBloodType.valueOf(bloodOrderRequest.bloodType),
                        rhType = UserRhType.valueOf(bloodOrderRequest.rhType),
                        unit = bloodOrderRequest.unit,
                        note = bloodOrderRequest.additionalNote
                )
        )
        return BloodStatusResponse(
                result = "Confirmed"
        )
    }


}
