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




    fun resetBloodbank() : WebBloodBankResponse {
        bloodBankRepository.deleteAll()
        bloodBankRepository.save(
                BloodBank(
                        ffp_0pos = 1000,
                        ffp_0neg = 1000,
                        ffp_Apos = 1000,
                        ffp_Aneg = 1000,
                        ffp_Bpos = 1000,
                        ffp_Bneg = 1000,
                        ffp_ABpos = 1000,
                        ffp_ABneg = 1000,
                        pc_0pos = 1000,
                        pc_0neg = 1000,
                        pc_Apos = 1000,
                        pc_Aneg = 1000,
                        pc_Bpos = 1000,
                        pc_Bneg = 1000,
                        pc_ABpos = 1000,
                        pc_ABneg = 1000
                )
        )
        bloodBankRepository.findAll()[0].bloodbankInitilize()
        return WebBloodBankResponse(
                ffp_0pos = 1000,
                ffp_0neg = 1000,
                ffp_Apos = 1000,
                ffp_Aneg = 1000,
                ffp_Bpos = 1000,
                ffp_Bneg = 1000,
                ffp_ABpos = 1000,
                ffp_ABneg = 1000,
                pc_0pos = 1000,
                pc_0neg = 1000,
                pc_Apos = 1000,
                pc_Aneg = 1000,
                pc_Bpos = 1000,
                pc_Bneg = 1000,
                pc_ABpos = 1000,
                pc_ABneg = 1000
        )
    }

    fun handleOrder(doctor: User,
                    language: Language,
                    bloodOrderRequest: BloodOrderRequest
    ): BloodStatusResponse {
        val bloodType = bloodOrderRequest.productType.decapitalize() + " "+ bloodOrderRequest.bloodType +
                bloodOrderRequest.rhType.substring(0,2).decapitalize()
        val bloodbank = bloodBankRepository.findAll()[0].bloodbankStorage
        var result = "Confirmed"
        val patient = if (bloodOrderRequest.patientId != null)
            userRepository.findById(bloodOrderRequest.patientId).orElseThrow {
                RestException("Auth.invalidUser", HttpStatus.BAD_REQUEST)
            }

        else null
        if(bloodOrderRequest.unit.toInt() <= bloodbank.get(bloodType)!!){
            bloodbank.put(bloodType, bloodbank.get(bloodType)!! - bloodOrderRequest.unit.toInt())
            bloodBankRepository.deleteAll()
            bloodBankRepository.save(
                    BloodBank(
                            ffp_0pos = bloodbank.get("ffp_0pos")!!,
                            ffp_0neg = bloodbank.get("ffp_0neg")!!,
                            ffp_Apos = bloodbank.get("ffp_Apos")!!,
                            ffp_Aneg = bloodbank.get("ffp_Aneg")!!,
                            ffp_Bpos = bloodbank.get("ffp_Bpos")!!,
                            ffp_Bneg = bloodbank.get("ffp_Bneg")!!,
                            ffp_ABpos = bloodbank.get("ffp_ABpos")!!,
                            ffp_ABneg = bloodbank.get("ffp_ABneg")!!,
                            pc_0pos = bloodbank.get("pc_0pos")!!,
                            pc_0neg = bloodbank.get("pc_0neg")!!,
                            pc_Apos = bloodbank.get("pc_Apos")!!,
                            pc_Aneg = bloodbank.get("pc_Aneg")!!,
                            pc_Bpos = bloodbank.get("pc_Bpos")!!,
                            pc_Bneg = bloodbank.get("pc_Bneg")!!,
                            pc_ABpos = bloodbank.get("pc_ABpos")!!,
                            pc_ABneg = bloodbank.get("pc_ABneg")!!
                    )
            )
            result = "Confirmed"
        }
        else{
            result = "Denied"
        }
        bloodOrderRepository.save(
                BloodOrder(
                        doctor = doctor,
                        doctorName = "Dr. "+userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor)!!.name+ " " +
                                userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor)!!.surname,
                        bloodType = UserBloodType.valueOf(bloodOrderRequest.bloodType),
                        rhType = UserRhType.valueOf(bloodOrderRequest.rhType),
                        productType = UserBloodOrderType.valueOf(bloodOrderRequest.productType),
                        bloodName = UserBloodOrderType.valueOf(bloodOrderRequest.productType).toString()+ " "+
                            UserBloodType.valueOf(bloodOrderRequest.bloodType).toString() + " Rh "+
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



    fun getBloodbank() : List<WebBloodBankResponse> {
        return bloodBankRepository.findAll().map {
            val bloodbank = bloodBankRepository.findAll().get(0).bloodbankStorage
            WebBloodBankResponse(
                    ffp_0pos = bloodbank.get("ffp_0pos")!!,
                    ffp_0neg = bloodbank.get("ffp_0neg")!!,
                    ffp_Apos = bloodbank.get("ffp_Apos")!!,
                    ffp_Aneg = bloodbank.get("ffp_Aneg")!!,
                    ffp_Bpos = bloodbank.get("ffp_Bpos")!!,
                    ffp_Bneg = bloodbank.get("ffp_Bneg")!!,
                    ffp_ABpos = bloodbank.get("ffp_ABpos")!!,
                    ffp_ABneg = bloodbank.get("ffp_ABneg")!!,
                    pc_0pos = bloodbank.get("pc_0pos")!!,
                    pc_0neg = bloodbank.get("pc_0neg")!!,
                    pc_Apos = bloodbank.get("pc_Apos")!!,
                    pc_Aneg = bloodbank.get("pc_Aneg")!!,
                    pc_Bpos = bloodbank.get("pc_Bpos")!!,
                    pc_Bneg = bloodbank.get("pc_Bneg")!!,
                    pc_ABpos = bloodbank.get("pc_ABpos")!!,
                    pc_ABneg = bloodbank.get("pc_ABneg")!!
            )
        }
    }

    fun getAllPreviousOrders() : List<WebBloodOrderResponse> {

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
                            doctorName = userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor.doctor)!!.name+ " " +
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


    fun getMedicalOrders(medical: User,language : Language): MedicalBloodOrderResponse {
        val doctor = doctorMedicalRelationshipRepository.findByMedical(medical)?.doctor
                ?: throw RestException("Auth.invalidUser", HttpStatus.BAD_REQUEST)
        val orderList = getDoctorsPreviousOrders(doctor,language)
        return  MedicalBloodOrderResponse(
                todoOrderList = orderList.filter { !it.isReady },
                doneOrderList = orderList.filter { it.isReady }

        )
    }


    fun addDoneTheOrder(orderId:Long){
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
                        doctorName = userBodyInfoRepository.findFirstByUserOrderByIdDesc(order.doctor)!!.name+ " " +
                                userBodyInfoRepository.findFirstByUserOrderByIdDesc(order.doctor)!!.surname,
                        patient = order.patient,
                        bloodType = order.bloodType,
                        rhType = order.rhType,
                        quantity = order.quantity,
                        units = order.unit.toInt(),
                        note = order.note,
                        productType = order.productType,
                        kind =order.kind,
                        unit = order.unit,
                        isReady = true
                )
        )

    }


}
