package com.example.coagusearch.modules.regularMedication.service

import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.regularMedication.model.DrugFrequencyRepository
import com.example.coagusearch.modules.regularMedication.model.DrugInfoRepository
import com.example.coagusearch.modules.regularMedication.model.UserRegularMedication
import com.example.coagusearch.modules.regularMedication.model.UserRegularMedicationRepository
import com.example.coagusearch.modules.regularMedication.request.MedicineInfoRequest
import com.example.coagusearch.modules.regularMedication.response.AllDrugInfoResponse
import com.example.coagusearch.modules.regularMedication.response.DrugFrequencyResponse
import com.example.coagusearch.modules.regularMedication.response.GetDrugRequest
import com.example.coagusearch.modules.regularMedication.response.GetFrequencyRequest
import com.example.coagusearch.modules.regularMedication.response.MedicineInfoResponse
import com.example.coagusearch.modules.regularMedication.response.UserMedicineResponse
import com.example.coagusearch.modules.users.model.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DrugService @Autowired constructor(
        private val drugInfoRepository: DrugInfoRepository,
        private val drugFrequencyRepository: DrugFrequencyRepository,
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
                userDrugs = userRegularMedicationRepository.findAllByUser(user).map {
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

    fun saveRegularMedicineInfo(user: User, language: Language, saveRegularMedicineInfoRequest: MedicineInfoRequest) {
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


    companion object {
        val logger = LoggerFactory.getLogger(DrugService::class.java)
    }
}
