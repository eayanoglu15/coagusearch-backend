package com.example.coagusearch.modules.regularMedication.service

import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.regularMedication.model.DrugFrequencyRepository
import com.example.coagusearch.modules.regularMedication.model.DrugInfoRepository
import com.example.coagusearch.modules.regularMedication.model.UserRegularMedication
import com.example.coagusearch.modules.regularMedication.model.UserRegularMedicationRepository
import com.example.coagusearch.modules.regularMedication.request.DeleteMedicineInfoRequest
import com.example.coagusearch.modules.regularMedication.request.MedicineInfoRequest
import com.example.coagusearch.modules.regularMedication.request.PatientRegularMedicationRequest
import com.example.coagusearch.modules.regularMedication.response.*
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserCaseType
import com.example.coagusearch.modules.users.model.UserDoctorPatientRelationshipRepository
import com.example.coagusearch.shared.RestException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DrugService @Autowired constructor(
        private val drugInfoRepository: DrugInfoRepository,
        private val drugFrequencyRepository: DrugFrequencyRepository,
        private val userDoctorPatientRelationshipRepository: UserDoctorPatientRelationshipRepository,
        private val userRegularMedicationRepository: UserRegularMedicationRepository
) {

    fun getAllDrugs(user: User, language: Language): AllDrugInfoResponse {
        val drugs = drugInfoRepository.findAll()
        val frequency = drugFrequencyRepository.findAll()
        return AllDrugInfoResponse(
                drugs = drugs.map {
                    GetDrugRequest(
                            key = it.key,
                            content = it.name
                    )
                },
                frequencies = frequency.map {
                    GetFrequencyRequest(
                            key = it.key,
                            title = it.detail.stringByLanguage(language)
                    )
                }
        )
    }



    fun getByUser(user: User, language: Language): UserMedicineResponse {
        return UserMedicineResponse(
                allDrugs = getAllDrugs(user, language),
                userDrugs = userRegularMedicationRepository.findAllByUserAndActive(user).map {
                    MedicineInfoResponse(
                            id = it.id!!,
                            mode = it.mode,
                            custom = it.custom,
                            key = it.drug?.key,
                            frequency = DrugFrequencyResponse(
                                    key = it.frequency?.key!!,
                                    title = it.frequency?.detail?.stringByLanguage(language)!!
                            ),
                            dosage = it.dosage
                    )
                }
        )
    }
    //TODO: Add Usertype check && check whether the patients belong the doctor or not!!
    fun getRegularMedicinesById(user: User, patient: PatientRegularMedicationRequest, language: Language): UserRegularMedicationResponse{
        var validPatientId : Long = 123
        if(userDoctorPatientRelationshipRepository.findByDoctor(user).filter {it.patient.id == patient.patientId}.size > 0){
            validPatientId = userDoctorPatientRelationshipRepository.findByDoctor(user).filter {it.patient.id == patient.patientId}[0].patient.id!!
        }
        if(user.type == UserCaseType.Doctor && validPatientId == patient.patientId){
            return UserRegularMedicationResponse(
                    patientDrugs = userRegularMedicationRepository.findAll().filter {
                        it.user.id == patient.patientId
                    }.map {
                        MedicineInfoResponse(
                                id = it.id!!,
                                mode = it.mode,
                                custom = it.custom,
                                key = it.drug?.key,
                                frequency = DrugFrequencyResponse(
                                        key = it.frequency?.key!!,
                                        title = it.frequency?.detail?.stringByLanguage(language)!!
                                ),
                                dosage = it.dosage
                        )
                    }
            )
        }
        else{
            throw RestException(
                    "You are either not a doctor or this user is not your patient.",
                    HttpStatus.UNAUTHORIZED,
                    "Medicine",
                    user.id!!
            )
        }

    }


    fun saveRegularMedicineInfo(user: User,
                                language: Language,
                                saveRegularMedicineInfoRequest: MedicineInfoRequest) {
        if(saveRegularMedicineInfoRequest.id != null){
            var medicine = userRegularMedicationRepository.findById(saveRegularMedicineInfoRequest.id)
                    .map { it }
                    .orElseThrow {  RestException(
                            "Exception.notFound",
                            HttpStatus.UNAUTHORIZED,
                            "Medicine",
                            saveRegularMedicineInfoRequest.id
                    ) }
            userRegularMedicationRepository.deleteById(medicine.id!!)
            userRegularMedicationRepository.save(
                    UserRegularMedication(
                            user = user,
                            mode = saveRegularMedicineInfoRequest.mode,
                            drug = drugInfoRepository.findByKey(saveRegularMedicineInfoRequest.key),
                            frequency = drugFrequencyRepository.findByKey(saveRegularMedicineInfoRequest.frequency),
                            dosage = saveRegularMedicineInfoRequest.dosage,
                            custom = saveRegularMedicineInfoRequest.customText,
                            active = true
                    )
            )
        }else{
            userRegularMedicationRepository.save(
                    UserRegularMedication(
                            user = user,
                            mode = saveRegularMedicineInfoRequest.mode,
                            drug = drugInfoRepository.findByKey(saveRegularMedicineInfoRequest.key),
                            frequency = drugFrequencyRepository.findByKey(saveRegularMedicineInfoRequest.frequency),
                            dosage = saveRegularMedicineInfoRequest.dosage,
                            custom = saveRegularMedicineInfoRequest.customText,
                            active = true
                    )
            )
        }


    }

    fun deleteRegularMedicineInfo(user: User, language: Language,
                                  deleteRegularMedicineInfoRequest: DeleteMedicineInfoRequest) {
        var medicine = userRegularMedicationRepository.findById(deleteRegularMedicineInfoRequest.medicineId)
                .map { it }
                .orElseThrow {  RestException(
                        "Exception.notFound",
                        HttpStatus.UNAUTHORIZED,
                        "Medicine",
                        deleteRegularMedicineInfoRequest.medicineId
                ) }
        userRegularMedicationRepository.deleteById(medicine.id!!)



    }


    companion object {
        val logger = LoggerFactory.getLogger(DrugService::class.java)
    }
}
