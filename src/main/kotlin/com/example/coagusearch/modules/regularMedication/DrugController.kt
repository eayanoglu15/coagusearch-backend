package com.example.coagusearch.modules.regularMedication

import com.example.coagusearch.modules.base.BaseController
import com.example.coagusearch.modules.base.model.toLanguage
import com.example.coagusearch.modules.regularMedication.request.DeleteMedicineInfoRequest
import com.example.coagusearch.modules.regularMedication.request.MedicineInfoRequest
import com.example.coagusearch.modules.regularMedication.request.PatientRegularMedicationRequest
import com.example.coagusearch.modules.regularMedication.response.AllDrugInfoResponse
import com.example.coagusearch.modules.regularMedication.response.UserMedicineResponse
import com.example.coagusearch.modules.regularMedication.response.UserRegularMedicationResponse
import com.example.coagusearch.modules.regularMedication.service.DrugService
import com.example.coagusearch.modules.users.request.PatientDetailRequest
import com.example.coagusearch.modules.users.response.PatientDetailScreen
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

/**
 * Endpoint for user data that is not related to authentication
 */
@RestController
@RequestMapping("/drug")
@Api(description = "Endpoint for getting and setting user specific data")
class DrugController @Autowired constructor(
        private val drugService: DrugService,
        private val messageSource: MessageSource
) : BaseController() {

    @GetMapping("/all")
    @ApiOperation(value = "Gets all drugs", response = AllDrugInfoResponse::class)
    fun getAllDrug(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<AllDrugInfoResponse> {
        val user = userPrincipal.user
        return drugService.getAllDrugs(user,locale.toLanguage()).asOkResponse()
    }

    @GetMapping("/getByUser")
    @ApiOperation(value = "Gets all drugs", response = UserMedicineResponse::class)
    fun getRegularMedicationOfUser(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<UserMedicineResponse> {
        val user = userPrincipal.user
        return drugService.getByUser(user,locale.toLanguage()).asOkResponse()
    }

    @PostMapping("/getPatientRegularMedication")
    @ApiOperation(value = "Gets requested patient's  drugs", response = UserRegularMedicationResponse::class)
    fun getPatientRegularMedication(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale,
            @Valid @RequestBody PatientRegularMedicationRequest: PatientRegularMedicationRequest
    ): ResponseEntity<UserRegularMedicationResponse> {
        val user = userPrincipal.user
        return drugService.getRegularMedicinesById(user, PatientRegularMedicationRequest, locale.toLanguage()).asOkResponse()
    }


    @PostMapping("/saveRegularMedicine")
    @ApiOperation(
            value = "Saves the regular medicine info.",
            response = ApiResponse::class
    )
    fun saveRegularMedicineInfo(
            @ApiParam(required = true, name = "saveStatusRequest", value = "Save Status Request object")
            @Valid @RequestBody saveRegularMedicineInfoRequest: MedicineInfoRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<UserMedicineResponse> {
        drugService.saveRegularMedicineInfo(user.user, locale.toLanguage(), saveRegularMedicineInfoRequest)
        return drugService.getByUser(user.user,locale.toLanguage()).asOkResponse()
    }


    @PostMapping("/deleteRegularMedication")
    @ApiOperation(
            value = "deletes the regular medicine info.",
            response = ApiResponse::class
    )
    fun deleteRegularMedicineInfo(
            @ApiParam(required = true, name = "deleteRegularMEdicatiın", value = "Save Status Request object")
            @Valid @RequestBody deleteRegularMedicineInfoRequest: DeleteMedicineInfoRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<UserMedicineResponse> {
        drugService.deleteRegularMedicineInfo(user.user, locale.toLanguage(), deleteRegularMedicineInfoRequest)
        return drugService.getByUser(user.user,locale.toLanguage()).asOkResponse()
    }
}
