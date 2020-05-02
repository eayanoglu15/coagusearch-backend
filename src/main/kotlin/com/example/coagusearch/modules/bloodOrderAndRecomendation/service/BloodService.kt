package com.example.coagusearch.modules.bloodOrderAndRecomendation.service

import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.BloodOrder
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.BloodOrderRepository
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.OrderKind
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.UserBloodOrderType
import com.example.coagusearch.modules.bloodOrderAndRecomendation.request.BloodOrderRequest
import com.example.coagusearch.modules.bloodOrderAndRecomendation.request.OrderForUserDataRequest
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.BloodStatusResponse
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.DoctorBloodOrderResponse
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.UserBloodOrderResponse
import com.example.coagusearch.modules.patientData.model.UserBloodTest
import com.example.coagusearch.modules.patientData.model.UserBloodTestRepository
import com.example.coagusearch.modules.patientData.service.PatientDataService
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserBloodType
import com.example.coagusearch.modules.users.model.UserBodyInfo
import com.example.coagusearch.modules.users.model.UserBodyInfoRepository
import com.example.coagusearch.modules.users.model.UserDoctorPatientRelationshipRepository
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
        private val userBodyInfoRepository: UserBodyInfoRepository,
        private val userBloodTestRepository: UserBloodTestRepository,
        private val doctorPatientRelationshipRepository: UserDoctorPatientRelationshipRepository
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
                        quantity = bloodOrderRequest.unit,
                        note = bloodOrderRequest.additionalNote,
                        productType = UserBloodOrderType.valueOf(bloodOrderRequest.productType),
                        kind = OrderKind.Blood,
                        unit = "Unit"
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
                    quantity = it.quantity,
                    additionalNote = if (it.note != null) it.note.toString() else null,
                    bloodTestId = it.bloodTest?.id!!,
                    kind = if (it.kind != null) it.kind.toString() else null,
                    unit = if (it.unit != null) it.unit else null
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
                    quantity = it.quantity,
                    additionalNote = if (it.note != null) it.note.toString() else null,
                    patientName = bodyInfo?.name,
                    patientSurname = bodyInfo?.surname,
                    bloodTestId = it.bloodTest?.id,
                    kind = if (it.kind != null) it.kind.toString() else null,
                    unit = if (it.unit != null) it.unit else null

            )
        }
    }


    fun saveOrderForBloodTest(orderForUserDataRequest: OrderForUserDataRequest) {
        val bloodTest = userBloodTestRepository.findById(orderForUserDataRequest.bloodTestId).orElseThrow {
            RestException(
                    "Exception.notFound",
                    HttpStatus.NOT_FOUND,
                    "BloodTest",
                    orderForUserDataRequest.bloodTestId
            )
        }
        val bodyInfo: UserBodyInfo? =  userBodyInfoRepository.findFirstByUserOrderByIdDesc(bloodTest.user)

        var doctor = doctorPatientRelationshipRepository.findByPatient(bloodTest.user)
        if (doctor != null)
            bloodOrderRepository.save(
                    BloodOrder(
                            doctor = doctor.doctor,
                            patient = bloodTest.user,
                            bloodType = bodyInfo?.bloodType,
                            rhType = bodyInfo?.rhType,
                            productType = UserBloodOrderType.valueOf(orderForUserDataRequest.product),
                            quantity = orderForUserDataRequest.quantity,
                            unit = orderForUserDataRequest.unit,
                            kind = OrderKind.valueOf(orderForUserDataRequest.kind),
                            bloodTest = bloodTest
                    )
            )
    }

    fun getOrdersOfData(bloodTest: UserBloodTest
    ): List<DoctorBloodOrderResponse> {
        return bloodOrderRepository.findAllByBloodTest(bloodTest).map {
            val bodyInfo: UserBodyInfo? = if (it.patient != null)
                userBodyInfoRepository.findFirstByUserOrderByIdDesc(it.patient!!)
            else null
            DoctorBloodOrderResponse(
                    bloodType = if (it.bloodType != null) it.bloodType.toString() else null,
                    rhType = if (it.rhType != null) it.rhType.toString() else null,
                    productType = if (it.productType != null) it.productType.toString() else null,
                    quantity = it.quantity,
                    additionalNote = if (it.note != null) it.note.toString() else null,
                    patientName = bodyInfo?.name,
                    patientSurname = bodyInfo?.surname,
                    bloodTestId = it.bloodTest?.id!!,
                    kind = if (it.kind != null) it.kind.toString() else null,
                    unit = if (it.unit != null) it.unit else null

            )
        }
    }

}
