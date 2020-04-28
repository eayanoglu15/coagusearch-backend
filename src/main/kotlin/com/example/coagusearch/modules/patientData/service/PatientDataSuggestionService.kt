package com.example.coagusearch.modules.patientData.service

import com.example.coagusearch.modules.patientData.model.BloodCategoryName
import com.example.coagusearch.modules.patientData.model.BloodTestName
import com.example.coagusearch.modules.patientData.model.BloodTestsRepository
import com.example.coagusearch.modules.patientData.model.UserBloodTestDataRepository
import com.example.coagusearch.modules.patientData.model.UserBloodTestRepository
import com.example.coagusearch.modules.patientData.response.SuggestionListResponse
import com.example.coagusearch.modules.patientData.response.SuggestionResponse
import com.example.coagusearch.modules.users.model.UserBodyInfoRepository
import com.example.coagusearch.shared.RestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component


@Component
class PatientDataSuggestionService @Autowired constructor(
        private val bloodTestRepository: BloodTestsRepository,
        private val userBloodTestRepository: UserBloodTestRepository,
        private val userBloodTestDataRepository: UserBloodTestDataRepository,
        private val patientDataService: PatientDataService,
        private val userBodyInfoRepository: UserBodyInfoRepository
) {
    companion object {
        val fibrinDeficit = SuggestionResponse(
                diagnosis = "Fibrin Deficit",
                unit = "g/kg",
                quantity = 4.0,
                kind = "Medicine",
                product = "Fibrinogen Concentrate"
        )
        val thrombinGenerationDeficit = SuggestionResponse(
                diagnosis = "Thrombin Generation Deficit",
                unit = "U/kg",
                quantity = 20.0,
                kind = "Medicine",
                product = "PCC"
        )
        val plateletDeficit = SuggestionResponse(
                diagnosis = "Platelet Deficit",
                unit = "Unit",
                quantity = 1.0,
                kind = "Blood",
                product = "Platelet Concentrate"
        )
        val severeDeficit1 = SuggestionResponse(
                diagnosis = "Severe Clot Deficit",
                unit = "mg/kg",
                quantity = 17.0,
                kind = "Medicine",
                product = "TXA"
        )
        val severeDeficit2 = SuggestionResponse(
                diagnosis = "Severe Clot Deficit",
                unit = "g/kg",
                quantity = 7.0,
                kind = "Medicine",
                product = "Fibrinojen Concentrate"
        )
        val severeDeficit3 = SuggestionResponse(
                diagnosis = "Severe Clot Deficit",
                unit = "U/kg",
                quantity = 25.0,
                kind = "Medicine",
                product = "PCC"
        )
        val severeDeficit4 = SuggestionResponse(
                diagnosis = "Severe Clot Deficit",
                unit = "Unit",
                quantity = 1.0,
                kind = "Blood",
                product = "Platelet Concentrate"
        )
    }

    fun suggestForData(bloodTestDataId: Long): SuggestionListResponse {
        val bloodTest = userBloodTestRepository.findById(bloodTestDataId).orElseThrow {
            RestException(
                    "Exception.notFound",
                    HttpStatus.NOT_FOUND,
                    "BloodTest",
                    bloodTestDataId
            )
        }
        val userBloodTestData = userBloodTestDataRepository.findByUserBloodTest(bloodTest)
        val fibtemA10: Double? = userBloodTestData.find {
            it.bloodTest.categoryName == BloodCategoryName.A10
                    && it.bloodTest.testName == BloodTestName.fibtem
        }?.value
        val extemCT: Double? = userBloodTestData.find {
            it.bloodTest.categoryName == BloodCategoryName.CT
                    && it.bloodTest.testName == BloodTestName.extem
        }?.value
        val extemA10: Double? = userBloodTestData.find {
            it.bloodTest.categoryName == BloodCategoryName.A10
                    && it.bloodTest.testName == BloodTestName.extem
        }?.value
        val platenetNumber: Int? = userBodyInfoRepository.findFirstByUserOrderByIdDesc(bloodTest.user)?.plateletNumber

        if (extemA10 != null && extemA10 < 30.0) {
            return SuggestionListResponse(
                    userSuggestionList = listOf(severeDeficit1, severeDeficit2, severeDeficit3, severeDeficit4)
            )
        } else {
            var mutableList: MutableList<SuggestionResponse> = mutableListOf<SuggestionResponse>()
            if (extemA10 != null && extemA10 < 40.0) {
                if (fibtemA10 != null && fibtemA10 > 12.0) {
                    if (platenetNumber != null && platenetNumber < 50000) {
                        mutableList.add(plateletDeficit)
                    }
                }

            }
            if (extemCT != null && extemCT > 80.0) {
                mutableList.add(thrombinGenerationDeficit)
            }
            if (fibtemA10 != null && fibtemA10 < 7.0) {
                mutableList.add(fibrinDeficit)
            }
            return SuggestionListResponse(
                    userSuggestionList = mutableList
            )
        }
    }


}