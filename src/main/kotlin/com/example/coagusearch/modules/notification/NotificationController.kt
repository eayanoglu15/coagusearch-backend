package com.example.coagusearch.modules.notification


import com.example.coagusearch.modules.base.BaseController
import com.example.coagusearch.modules.base.model.toLanguage
import com.example.coagusearch.modules.notification.request.CallPatientRequest
import com.example.coagusearch.modules.notification.request.NotifyMedicalRequest
import com.example.coagusearch.modules.notification.response.NotificationResponse
import com.example.coagusearch.modules.notification.service.NotificationService
import com.example.coagusearch.security.CurrentUser
import com.example.coagusearch.security.UserPrincipal
import com.example.coagusearch.shared.ApiResponse
import com.example.coagusearch.shared.asOkResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/notification")
@Validated
class NotificationController @Autowired constructor(
        val messageSource: MessageSource,
        val notificaitonService: NotificationService
) : BaseController() {

    @PostMapping("/notify-medical")
    fun notifyMedical(
            @Valid @RequestBody notifyMedicalRequest: NotifyMedicalRequest,
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale,
            httpServletRequest: HttpServletRequest
    ): ResponseEntity<ApiResponse> {
        notificaitonService.notifyMedical(userPrincipal.user,notifyMedicalRequest)
        return ApiResponse.fromMessage(messageSource, locale,
                true, "General.successfulSave").asOkResponse()
    }

    @PostMapping("/callPatient")
    fun callPatient(
            @Valid @RequestBody callPatientRequest: CallPatientRequest,
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale,
            httpServletRequest: HttpServletRequest
    ): ResponseEntity<ApiResponse> {
        notificaitonService.callPatient(userPrincipal.user,callPatientRequest)
        return ApiResponse.fromMessage(messageSource, locale,
                true, "General.successfulSave").asOkResponse()
    }

    @GetMapping("/page")
    fun getNotificationPage(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<List<NotificationResponse>> {
        val language = locale.toLanguage()
        return notificaitonService.getNotificationPage(userPrincipal.user,language).asOkResponse()
    }









}
