package com.example.coagusearch.modules.bloodOrderAndRecomendation

import com.example.coagusearch.modules.base.BaseController
import com.example.coagusearch.modules.base.model.toLanguage
import com.example.coagusearch.modules.bloodOrderAndRecomendation.request.BloodOrderRequest
import com.example.coagusearch.modules.bloodOrderAndRecomendation.response.BloodStatusResponse
import com.example.coagusearch.modules.bloodOrderAndRecomendation.service.BloodService
import com.example.coagusearch.security.CurrentUser
import com.example.coagusearch.security.UserPrincipal
import com.example.coagusearch.shared.asOkResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/blood")
@Api(description = "Endpoint for getting and setting user specific data")
class BloodOrderController @Autowired constructor(
        private val bloodService: BloodService,
        private val messageSource: MessageSource
) : BaseController() {


    @PostMapping("/order")
    @ApiOperation(value = "Order blood", response = BloodStatusResponse::class)
    fun getCurrentUser(
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale,
            @Valid @RequestBody bloodOrderRequest: BloodOrderRequest
    ): ResponseEntity<BloodStatusResponse> {
        val user = userPrincipal.user
        return bloodService.handleOrder(user,locale.toLanguage(),bloodOrderRequest).asOkResponse()
    }




}
