package com.example.coagusearch.modules.appointment


import com.example.coagusearch.backend.FirebaseNotificationSystem.FirebaseNotificationSender
import com.example.coagusearch.modules.appointment.request.SaveAppointmentsForUserRequest
import com.example.coagusearch.modules.appointment.response.UserAppointmentResponse
import com.example.coagusearch.modules.appointment.response.WeeklyAvalibilityResponse
import com.example.coagusearch.modules.appointment.service.AppointmentDataMapService
import com.example.coagusearch.modules.base.BaseController
import com.example.coagusearch.modules.base.model.toLanguage
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
@RequestMapping("/appointment")
@Api(description = "Endpoint for getting and setting user specific data")
class AppointmentController @Autowired constructor(
        private val userService: UserService,
        private val appointmentDataMapService: AppointmentDataMapService,
        private val messageSource: MessageSource,
        private val firebaseNotificationSender: FirebaseNotificationSender
) : BaseController() {

    @GetMapping("/getAvailableTimes")
    @ApiOperation(value = "Gets appointment time by user", response = WeeklyAvalibilityResponse::class)
    fun getAvailableTimes(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<WeeklyAvalibilityResponse> {
        val user = userPrincipal.user
        return appointmentDataMapService.getAppointmentTimes(user).asOkResponse()
    }

    @GetMapping("/getByUser")
    @ApiOperation(value = "Gets appointment time by user", response = UserAppointmentResponse::class)
    fun getByUserAppointment(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<UserAppointmentResponse> {
        val user = userPrincipal.user
        return appointmentDataMapService.getByUser(user,locale.toLanguage()).asOkResponse()
    }



    @GetMapping("/notificationTest")
    fun test(
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<com.example.coagusearch.shared.ApiResponse> {
        firebaseNotificationSender.testNotification()
        return com.example.coagusearch.shared.ApiResponse.fromMessage(messageSource, locale,
                true, "General.successfulSave").asOkResponse()
    }



    @PostMapping("/save")
    @ApiOperation(
            value = "Saves appointment for doctor.",
            response = com.example.coagusearch.shared.ApiResponse::class
    )
    fun saveRegularMedicineInfo(
            @Valid @RequestBody saveAppointmentsForUserRequest: SaveAppointmentsForUserRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<com.example.coagusearch.shared.ApiResponse> {
        appointmentDataMapService.saveAppointmentForPatient(user.user,saveAppointmentsForUserRequest)
        return com.example.coagusearch.shared.ApiResponse.fromMessage(messageSource, locale,
                true, "General.successfulSave").asOkResponse()
    }
}
