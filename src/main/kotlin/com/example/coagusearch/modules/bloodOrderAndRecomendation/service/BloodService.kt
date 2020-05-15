package com.example.coagusearch.modules.bloodOrderAndRecomendation.service

import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.bloodOrderAndRecomendation.model.*
import com.example.coagusearch.modules.bloodOrderAndRecomendation.request.BloodOrderRequest
import com.example.coagusearch.modules.bloodOrderAndRecomendation.request.OrderForUserDataRequest
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.*
import com.example.coagusearch.modules.patientData.model.UserBloodTest
import com.example.coagusearch.modules.patientData.model.UserBloodTestRepository
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserBloodType
import com.example.coagusearch.modules.users.model.UserBodyInfo
import com.example.coagusearch.modules.users.model.UserBodyInfoRepository
import com.example.coagusearch.modules.users.model.UserDoctorMedicalRelationshipRepository
import com.example.coagusearch.modules.users.model.UserDoctorPatientRelationshipRepository
import com.example.coagusearch.modules.users.model.UserRepository
import com.example.coagusearch.modules.users.model.UserRhType
import com.example.coagusearch.shared.RestException
import com.example.coagusearch.shared.asList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class BloodService @Autowired constructor(
        private val bloodOrderRepository: BloodOrderRepository,
        private val bloodBankRepository: BloodBankRepository,
        private val userRepository: UserRepository,
        private val userBodyInfoRepository: UserBodyInfoRepository,
        private val userBloodTestRepository: UserBloodTestRepository,
        private val doctorPatientRelationshipRepository: UserDoctorPatientRelationshipRepository,
        private val doctorMedicalRelationshipRepository: UserDoctorMedicalRelationshipRepository
) {


    fun resetBloodbank(): WebBloodBankResponse {
        bloodBankRepository.deleteAll()
        BloodBank.keyList.map {
            bloodBankRepository.save(
                    BloodBank(
                            key = it,
                            value = 1000
                    )
            )

        }
        val allBloodBank = bloodBankRepository.findAll()
        return WebBloodBankResponse(
                ffp_0pos = allBloodBank.find{it.key =="ffp_0pos" }?.value ?: 990,
                ffp_0neg = allBloodBank.find{it.key =="ffp_0neg" }?.value ?: 990,
                ffp_Apos = allBloodBank.find{it.key =="ffp_Apos" }?.value ?: 990,
                ffp_Aneg = allBloodBank.find{it.key =="ffp_Aneg" }?.value ?: 990,
                ffp_Bpos = allBloodBank.find{it.key =="ffp_Bpos" }?.value ?: 990,
                ffp_Bneg = allBloodBank.find{it.key =="ffp_Bneg" }?.value ?: 990,
                ffp_ABpos = allBloodBank.find{it.key =="ffp_ABpos" }?.value ?: 990,
                ffp_ABneg = allBloodBank.find{it.key =="ffp_ABneg" }?.value ?: 990,
                pc_0pos = allBloodBank.find{it.key =="pc_0pos" }?.value ?: 990,
                pc_0neg = allBloodBank.find{it.key =="pc_0neg" }?.value ?: 990,
                pc_Apos = allBloodBank.find{it.key =="pc_Apos" }?.value ?: 990,
                pc_Aneg = allBloodBank.find{it.key =="pc_Aneg" }?.value ?: 990,
                pc_Bpos = allBloodBank.find{it.key =="pc_Bpos" }?.value ?: 990,
                pc_Bneg = allBloodBank.find{it.key =="pc_Bneg" }?.value ?: 990,
                pc_ABpos = allBloodBank.find{it.key =="pc_ABpos" }?.value ?: 990,
                pc_ABneg = allBloodBank.find{it.key =="pc_ABneg" }?.value ?: 990
        )
    }

    fun handleOrder(doctor: User,
                    language: Language,
                    bloodOrderRequest: BloodOrderRequest
    ): BloodStatusResponse {
        val patient = if (bloodOrderRequest.patientId != null)
            userRepository.findById(bloodOrderRequest.patientId).orElseThrow {
                RestException("Auth.invalidUser", HttpStatus.BAD_REQUEST)
            }
        else null
        var result = "Confirmed"
        // TODO: Handle PlateletConcentrate
        val bloodType = bloodOrderRequest.productType.decapitalize() + " " + bloodOrderRequest.bloodType +
                bloodOrderRequest.rhType.substring(0, 2).decapitalize()
        val bloodbank = bloodBankRepository.findByKey(bloodType) ?: return BloodStatusResponse("Denied")
        if (bloodOrderRequest.unit.toInt() <= bloodbank.value) {
            bloodBankRepository.deleteByKey(bloodbank.key)
            bloodBankRepository.save(
                    BloodBank(
                           key= bloodbank.key,
                            value = bloodbank.value - bloodOrderRequest.unit.toInt()
                    )
            )
            result = "Confirmed"
        } else {
            result = "Denied"
        }
        bloodOrderRepository.save(
                BloodOrder(
                        doctor = doctor,
                        doctorName = "Dr. " + userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor)!!.name + " " +
                                userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor)!!.surname,
                        bloodType = UserBloodType.valueOf(bloodOrderRequest.bloodType),
                        rhType = UserRhType.valueOf(bloodOrderRequest.rhType),
                        productType = UserBloodOrderType.valueOf(bloodOrderRequest.productType),
                        bloodName = UserBloodOrderType.valueOf(bloodOrderRequest.productType).toString() + " " +
                                UserBloodType.valueOf(bloodOrderRequest.bloodType).toString() + " Rh " +
                                UserRhType.valueOf(bloodOrderRequest.rhType).toString(),
                        quantity = bloodOrderRequest.unit,
                        units = bloodOrderRequest.unit.toInt(),
                        note = bloodOrderRequest.additionalNote,
                        kind = OrderKind.Blood,
                        unit = "Unit",
                        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString(),
                        time = LocalTime.now(),
                        status = result

                )
        )
        return BloodStatusResponse(
                result = result
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
                    bloodTestId = it.bloodTest?.id,
                    kind = if (it.kind != null) it.kind.toString() else null,
                    unit = if (it.unit != null) it.unit else null,
                    isReady = it.isReady,
                    bloodOrderId = it.id!!
            )
        }
    }


    fun getBloodbank(): List<WebBloodBankResponse> {
        val allBloodBank = bloodBankRepository.findAll()
        return WebBloodBankResponse(
                ffp_0pos = allBloodBank.find{it.key =="ffp_0pos" }?.value ?: 990,
                ffp_0neg = allBloodBank.find{it.key =="ffp_0neg" }?.value ?: 990,
                ffp_Apos = allBloodBank.find{it.key =="ffp_Apos" }?.value ?: 990,
                ffp_Aneg = allBloodBank.find{it.key =="ffp_Aneg" }?.value ?: 990,
                ffp_Bpos = allBloodBank.find{it.key =="ffp_Bpos" }?.value ?: 990,
                ffp_Bneg = allBloodBank.find{it.key =="ffp_Bneg" }?.value ?: 990,
                ffp_ABpos = allBloodBank.find{it.key =="ffp_ABpos" }?.value ?: 990,
                ffp_ABneg = allBloodBank.find{it.key =="ffp_ABneg" }?.value ?: 990,
                pc_0pos = allBloodBank.find{it.key =="pc_0pos" }?.value ?: 990,
                pc_0neg = allBloodBank.find{it.key =="pc_0neg" }?.value ?: 990,
                pc_Apos = allBloodBank.find{it.key =="pc_Apos" }?.value ?: 990,
                pc_Aneg = allBloodBank.find{it.key =="pc_Aneg" }?.value ?: 990,
                pc_Bpos = allBloodBank.find{it.key =="pc_Bpos" }?.value ?: 990,
                pc_Bneg = allBloodBank.find{it.key =="pc_Bneg" }?.value ?: 990,
                pc_ABpos = allBloodBank.find{it.key =="pc_ABpos" }?.value ?: 990,
                pc_ABneg = allBloodBank.find{it.key =="pc_ABneg" }?.value ?: 990
        ).asList()
    }

    fun getAllPreviousOrders(): List<WebBloodOrderResponse> {

        return bloodOrderRepository.findAll().map {
            WebBloodOrderResponse(
                    req_date = it.date,
                    req_time = it.time,
                    blood_type_name = it.bloodName,
                    units = it.units,
                    requester_name = it.doctorName,
                    isConfirmed = it.status

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
                    unit = if (it.unit != null) it.unit else null,
                    isReady = it.isReady,
                    bloodOrderId = it.id!!

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
        val bodyInfo: UserBodyInfo? = userBodyInfoRepository.findFirstByUserOrderByIdDesc(bloodTest.user)

        var doctor = doctorPatientRelationshipRepository.findByPatient(bloodTest.user)
        if (doctor != null)
            bloodOrderRepository.save(
                    BloodOrder(
                            doctor = doctor.doctor,
                            doctorName = userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor.doctor)!!.name + " " +
                                    userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor.doctor)!!.surname,
                            patient = bloodTest.user,
                            bloodType = bodyInfo?.bloodType,
                            rhType = bodyInfo?.rhType,
                            productType = UserBloodOrderType.valueOf(orderForUserDataRequest.product),
                            quantity = orderForUserDataRequest.quantity,
                            units = orderForUserDataRequest.quantity.toInt(),
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
                    bloodTestId = it.bloodTest?.id,
                    kind = if (it.kind != null) it.kind.toString() else null,
                    unit = if (it.unit != null) it.unit else null,
                    isReady = it.isReady,
                    bloodOrderId = it.id!!

            )
        }
    }


    fun getMedicalOrders(medical: User, language: Language): MedicalBloodOrderResponse {
        val doctor = doctorMedicalRelationshipRepository.findByMedical(medical)?.doctor
                ?: throw RestException("Auth.invalidUser", HttpStatus.BAD_REQUEST)
        val orderList = getDoctorsPreviousOrders(doctor, language)
        return MedicalBloodOrderResponse(
                todoOrderList = orderList.filter { !it.isReady },
                doneOrderList = orderList.filter { it.isReady }

        )
    }


    fun addDoneTheOrder(orderId: Long) {
        var order = bloodOrderRepository.findById(orderId).orElseThrow {
            RestException(
                    "Exception.notFound",
                    HttpStatus.NOT_FOUND,
                    "Blood Order",
                    orderId
            )
        }
        bloodOrderRepository.deleteById(orderId)
        bloodOrderRepository.save(
                BloodOrder(
                        doctor = order.doctor,
                        doctorName = userBodyInfoRepository.findFirstByUserOrderByIdDesc(order.doctor)!!.name + " " +
                                userBodyInfoRepository.findFirstByUserOrderByIdDesc(order.doctor)!!.surname,
                        patient = order.patient,
                        bloodType = order.bloodType,
                        rhType = order.rhType,
                        quantity = order.quantity,
                        units = order.unit.toInt(),
                        note = order.note,
                        productType = order.productType,
                        kind = order.kind,
                        unit = order.unit,
                        isReady = true
                )
        )

    }


}
