package com.example.coagusearch.modules.patientData.service

import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.bloodOrderAndRecomendation.service.BloodService
import com.example.coagusearch.modules.patientData.model.BloodTestsRepository
import com.example.coagusearch.modules.patientData.model.UserBloodTest
import com.example.coagusearch.modules.patientData.model.UserBloodTestDataRepository
import com.example.coagusearch.modules.patientData.model.UserBloodTestRepository
import com.example.coagusearch.modules.patientData.response.BloodDateResponse
import com.example.coagusearch.modules.patientData.response.UserBloodTestDataCategoryResponse
import com.example.coagusearch.modules.patientData.response.UserBloodTestDataResponse
import com.example.coagusearch.modules.patientData.response.UserBloodTestDataSpesificResponse
import com.example.coagusearch.modules.patientData.response.UserBloodTestHistoryResponse
import com.example.coagusearch.modules.patientData.response.UserBloodTestsResponse
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.shared.RestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.ZoneId


@Service
class PatientDataService @Autowired constructor(
        private val bloodTestRepository: BloodTestsRepository,
        private val userBloodTestRepository: UserBloodTestRepository,
        private val userBloodTestDataRepository: UserBloodTestDataRepository,
        private val bloodService: BloodService
) {


    fun getTestById(bloodTestId: KeyType): UserBloodTest{
      return  userBloodTestRepository.findById(bloodTestId).orElseThrow {
            RestException(
                    "Exception.notFound",
                    HttpStatus.NOT_FOUND,
                    "BloodTest",
                    bloodTestId
            )
        }
    }

    fun getUsersBloodTests(user: User,
                           language: Language): UserBloodTestsResponse {
        return UserBloodTestsResponse(
                userTestList = userBloodTestRepository.findAllByUser(user).map {
                    val date = it.testedAt.atZone(ZoneId.of("Europe/Istanbul"))
                    UserBloodTestHistoryResponse(
                            id = it.id!!,
                            testDate = BloodDateResponse(
                                    day = date.dayOfMonth,
                                    month = date.monthValue,
                                    year = date.year
                            )
                    )
                }
        )
    }

    fun getPatientLastAnalysis(patient: User): UserBloodTestHistoryResponse? {
        val bloodTest = userBloodTestRepository.findFirstByUserOrderByTestedAtDesc(patient) ?: return null
        val date = bloodTest.testedAt.atZone(ZoneId.of("Europe/Istanbul"))
        return UserBloodTestHistoryResponse(
                id = bloodTest.id!!,
                testDate = BloodDateResponse(
                        day = date.dayOfMonth,
                        month = date.monthValue,
                        year = date.year
                )
        )
    }


    fun getUsersBloodTestsDataById(bloodTestId: KeyType): UserBloodTestDataResponse {
        val bloodTest = userBloodTestRepository.findById(bloodTestId).orElseThrow {
            RestException(
                    "Exception.notFound",
                    HttpStatus.NOT_FOUND,
                    "BloodTest",
                    bloodTestId
            )
        }
        val userBloodTestData = userBloodTestDataRepository
                .findByUserBloodTest(bloodTest).groupBy { it.bloodTest.testName }
        return UserBloodTestDataResponse(
                bloodTestId = bloodTest.id!!,
                userBloodData = userBloodTestData.entries.map { entry ->
                    UserBloodTestDataSpesificResponse(
                            testName = entry.key.toString(),
                            categoryList = entry.value.map {
                                if (it.value != null)
                                    UserBloodTestDataCategoryResponse(
                                            categoryName = it.bloodTest.categoryName.toString(),
                                            maximumValue = it.bloodTest.maximum,
                                            minimumValue = it.bloodTest.minimum,
                                            optimalMaximumValue = it.bloodTest.optimumMaximum,
                                            optimalMinimumValue = it.bloodTest.optimumMinimum,
                                            value = it.value!!
                                    )
                                else null

                            }.filterNotNull()
                    )

                },
                ordersOfData = bloodService.getOrdersOfData(bloodTest)

        )
    }

    fun getLastUsersBloodTestsDataById(patient: User): UserBloodTestDataResponse? {
        val bloodTest = userBloodTestRepository.findFirstByUserOrderByTestedAtDesc(patient) ?: return null
        val userBloodTestData = userBloodTestDataRepository
                .findByUserBloodTest(bloodTest).groupBy { it.bloodTest.testName }
        return UserBloodTestDataResponse(
                bloodTestId = bloodTest.id!!,
                userBloodData = userBloodTestData.entries.map { entry ->
                    UserBloodTestDataSpesificResponse(
                            testName = entry.key.toString(),
                            categoryList = entry.value.map {
                                if (it.value != null)
                                    UserBloodTestDataCategoryResponse(
                                            categoryName = it.bloodTest.categoryName.toString(),
                                            maximumValue = it.bloodTest.maximum,
                                            minimumValue = it.bloodTest.minimum,
                                            optimalMaximumValue = it.bloodTest.optimumMaximum,
                                            optimalMinimumValue = it.bloodTest.optimumMinimum,
                                            value = it.value!!
                                    )
                                else null

                            }.filterNotNull()
                    )

                },
                ordersOfData = bloodService.getOrdersOfData(bloodTest)
        )
    }


}

