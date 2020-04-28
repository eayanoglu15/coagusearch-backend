package com.example.coagusearch.modules.patientData

import com.example.coagusearch.modules.base.BaseController
import com.example.coagusearch.modules.base.model.toLanguage
import com.example.coagusearch.modules.patientData.request.GetPatientBloodTestDataRequest
import com.example.coagusearch.modules.patientData.request.GetPatientBloodTestRequest
import com.example.coagusearch.modules.patientData.response.SuggestionListResponse
import com.example.coagusearch.modules.patientData.response.UserBloodTestDataResponse
import com.example.coagusearch.modules.patientData.response.UserBloodTestsResponse
import com.example.coagusearch.modules.patientData.service.PatientDataService
import com.example.coagusearch.modules.patientData.service.PatientDataSuggestionService
import com.example.coagusearch.modules.regularMedication.request.DeleteMedicineInfoRequest
import com.example.coagusearch.modules.regularMedication.request.MedicineInfoRequest
import com.example.coagusearch.modules.regularMedication.request.PatientRegularMedicationRequest
import com.example.coagusearch.modules.regularMedication.response.AllDrugInfoResponse
import com.example.coagusearch.modules.regularMedication.response.UserMedicineResponse
import com.example.coagusearch.modules.regularMedication.response.UserRegularMedicationResponse
import com.example.coagusearch.modules.regularMedication.service.DrugService
import com.example.coagusearch.modules.users.response.UserResponse
import com.example.coagusearch.modules.users.service.UserService
import com.example.coagusearch.security.CurrentUser
import com.example.coagusearch.security.UserPrincipal
import com.example.coagusearch.shared.asOkResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/patientData")
@Api(description = "Endpoint for getting and setting user specific data")
class PatientDataController @Autowired constructor(
        private val userService: UserService,
        private val patientDataService: PatientDataService,
        private val patientDataSuggestionService : PatientDataSuggestionService,
        private val messageSource: MessageSource
) : BaseController() {
    //TODO: check wheter doctor is patient's doctor
    @PostMapping("/getAllBloodTest")
    @ApiOperation(value = "Gets the current user", response = UserBloodTestsResponse::class)
    fun getPatientAllBloodTest(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale,
            @Valid @RequestBody getPatientBloodTestRequest: GetPatientBloodTestRequest
    ): ResponseEntity<UserBloodTestsResponse> {
        val patient = userService.getUserById(getPatientBloodTestRequest.patientId)
        return patientDataService.getUsersBloodTests(patient,locale.toLanguage()).asOkResponse()
    }


    @PostMapping("/getPatientBloodDataById")
    @ApiOperation(value = "Gets the current user", response = UserBloodTestDataResponse::class)
    fun getPatientBloodTestById(
            @Valid @RequestBody getPatientBloodTestDataRequest: GetPatientBloodTestDataRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<UserBloodTestDataResponse> {
        return patientDataService
                .getUsersBloodTestsDataById(getPatientBloodTestDataRequest.bloodTestDataId).asOkResponse()
    }


    @PostMapping("/getLastOfPatient")
    @ApiOperation(value = "Gets the current user", response = UserBloodTestDataResponse::class)
    fun getPatientLastBloodTest(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale,
            @Valid @RequestBody getPatientBloodTestRequest: GetPatientBloodTestRequest
    ): ResponseEntity<UserBloodTestDataResponse?> {
        val patient = userService.getUserById(getPatientBloodTestRequest.patientId)
        return patientDataService.getLastUsersBloodTestsDataById(patient).asOkResponse()
    }


    @PostMapping("/getSuggestionOfBloodTest")
    @ApiOperation(value = "Gets the current user", response = SuggestionListResponse::class)
    fun getSuggestionsOfData(
            @Valid @RequestBody getPatientBloodTestDataRequest: GetPatientBloodTestDataRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<SuggestionListResponse> {
        return patientDataSuggestionService
                .suggestForData(getPatientBloodTestDataRequest.bloodTestDataId).asOkResponse()
    }
}