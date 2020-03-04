package com.example.coagusearch.modules.users

import com.example.coagusearch.modules.base.BaseController
import com.example.coagusearch.modules.users.response.UserResponse
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

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
}
