package com.example.coagusearch.modules.bloodOrderAndRecomendation.service

import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.BloodOrder
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.BloodOrderRepository
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.UserBloodOrderType
import com.example.coagusearch.modules.bloodOrderAndRecomendation.request.BloodOrderRequest
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.BloodStatusResponse
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.DoctorBloodOrderResponse
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.UserBloodOrderResponse
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserBloodType
import com.example.coagusearch.modules.users.model.UserBodyInfo
import com.example.coagusearch.modules.users.model.UserBodyInfoRepository
import com.example.coagusearch.modules.users.model.UserRepository
import com.example.coagusearch.modules.users.model.UserRhType
import com.example.coagusearch.modules.users.service.UserService
import com.example.coagusearch.shared.RestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BloodService @Autowired constructor(
        private val bloodOrderRepository: BloodOrderRepository,
        private val userRepository: UserRepository,
        private val userBodyInfoRepository: UserBodyInfoRepository
) {
    fun handleOrder(doctor: User,
                    language: Language,
                    bloodOrderRequest: BloodOrderRequest
    ): BloodStatusResponse {
        val patient = if (bloodOrderRequest.patientId != null)
            userRepository.findById(bloodOrderRequest.patientId).orElseThrow {
                RestException("Auth.invalidUser", HttpStatus.BAD_REQUEST)
            }
        else null
        bloodOrderRepository.save(
                BloodOrder(
                        doctor = doctor,
                        patient = patient,
                        bloodType = UserBloodType.valueOf(bloodOrderRequest.bloodType),
                        rhType = UserRhType.valueOf(bloodOrderRequest.rhType),
                        unit = bloodOrderRequest.unit,
                        note = bloodOrderRequest.additionalNote,
                        productType = UserBloodOrderType.valueOf(bloodOrderRequest.productType)
                )
        )
        return BloodStatusResponse(
                result = "Confirmed"
        )
    }

    fun getPreviousOrdersByPatient(patient: User,
                                   language: Language
    ): List<UserBloodOrderResponse> {
        return bloodOrderRepository.findAllByPatient(patient = patient).map {
            UserBloodOrderResponse(
                    bloodType = if (it.bloodType != null) it.bloodType.toString() else null,
                    rhType = if (it.rhType != null) it.rhType.toString() else null,
                    productType = if (it.productType != null) it.productType.toString() else null,
                    unit = it.unit,
                    additionalNote = if (it.note != null) it.note.toString() else null
            )
        }
    }

    fun getDoctorsPreviousOrders(doctor: User,
                                 language: Language
    ): List<DoctorBloodOrderResponse> {
        return bloodOrderRepository.findAllByDoctor(doctor = doctor).map {
            val bodyInfo: UserBodyInfo? = if (it.patient != null)
                userBodyInfoRepository.findFirstByUserOrderByIdDesc(it.patient!!)
            else null

            DoctorBloodOrderResponse(
                    bloodType = if (it.bloodType != null) it.bloodType.toString() else null,
                    rhType = if (it.rhType != null) it.rhType.toString() else null,
                    productType = if (it.productType != null) it.productType.toString() else null,
                    unit = it.unit,
                    additionalNote = if (it.note != null) it.note.toString() else null,
                    patientName = bodyInfo?.name,
                    patientSurname = bodyInfo?.surname

            )
        }
    }


}
