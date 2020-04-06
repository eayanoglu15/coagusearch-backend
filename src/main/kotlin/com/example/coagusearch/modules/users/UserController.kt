package com.example.coagusearch.modules.users

import com.example.coagusearch.modules.base.BaseController
import com.example.coagusearch.modules.base.model.toLanguage
import com.example.coagusearch.modules.users.request.PatientDetailRequest
import com.example.coagusearch.shared.ApiResponse
import com.example.coagusearch.modules.users.response.UserResponse
import com.example.coagusearch.modules.users.request.UserBodyInfoSaveRequest
import com.example.coagusearch.modules.users.response.DoctorMainScreen
import com.example.coagusearch.modules.users.response.PatientDetailScreen
import com.example.coagusearch.modules.users.response.PatientMainScreen
import com.example.coagusearch.modules.users.service.UserService
import com.example.coagusearch.security.CurrentUser
import com.example.coagusearch.security.UserPrincipal
import com.example.coagusearch.shared.asOkResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
@RequestMapping("/users")
@Api(description = "Endpoint for getting and setting user specific data")
class UserController @Autowired constructor(
        private val userService: UserService,
        private val messageSource: MessageSource
) : BaseController() {
    /**
     * Endpoint for getting the current authenticated user
     * @param user Authenticated user
     * @return User as a response
     * @throws Exception when the id of the authenticated user is not found in the database
     */
    @GetMapping("/me")
    @ApiOperation(value = "Gets the current user", response = UserResponse::class)
    fun getCurrentUser(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<UserResponse> {
        val user = userPrincipal.user
        return userService.getMyUserResponse(user).asOkResponse()
    }

    @GetMapping("/getMyPatients")
    fun getMyPatients(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<List<UserResponse>> {
        val user = userPrincipal.user
        return userService.getMyPatients(user).asOkResponse()
    }


    @PostMapping("/saveBodyInfo")
    fun saveAUser(
            @Valid @RequestBody userBodyInfoSaveRequest: UserBodyInfoSaveRequest,
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<ApiResponse> {
        userService.saveBodyInfoByUser(userPrincipal.user, userBodyInfoSaveRequest).asOkResponse()
        return ApiResponse.fromMessage(messageSource, locale,
                true, "General.successfulSave").asOkResponse()
    }

    @GetMapping("/getPatientMainScreen")
    fun getPatientMainScreen(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<PatientMainScreen> {
        return userService.getPatientMainScreen(userPrincipal.user).asOkResponse()
    }

    @GetMapping("/getDoctorMainScreen")
    fun getDoctorMainScreen(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<DoctorMainScreen> {
        return userService.getDoctorMainScreen(userPrincipal.user).asOkResponse()
    }

    @PostMapping("/getPatientDetail")
    fun getPatientDetail(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale,
            @Valid @RequestBody patientDetailRequest: PatientDetailRequest
    ): ResponseEntity<PatientDetailScreen> {
        return userService.getPatientDetailScreen(
                userPrincipal.user,
                patientDetailRequest.patientId,
                locale.toLanguage()
        ).asOkResponse()
    }


}
