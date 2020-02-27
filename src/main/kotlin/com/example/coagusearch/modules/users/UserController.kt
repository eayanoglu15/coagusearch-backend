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


    /**
     * Endpoint for updating user email
     * @param newEmailRequest Request object obtained by the request body containing new email
     * @param user Authenticated user
     * @param locale User locale specified by the headers
     * @return Response given to the update request, either success or failure
     *         if the email is already found in the database
     * @throws Exception when the id of the authenticated user is not found in the database

    @PostMapping("/update/email")
    @ApiOperation(value = "Updates user's email", response = ApiResponse::class)
    fun updateEmail(
            @ApiParam(required = true, name = "newPhoneRequest", value = "Phone Request Object")
        @Valid @RequestBody newEmailRequest: UpdateEmailRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<ApiResponse> {
        val email = newEmailRequest.email
        if (userService.existsByEmail(email)) {
            return ResponseEntity(
                    ApiResponse.fromMessage(messageSource, locale, false, "Auth.emailInUse"),
                    HttpStatus.BAD_REQUEST
            )
        }
        return user.user
                .let { userService.updateUser(it.copy(email = email)) }
                .let { ApiResponse.fromMessage(messageSource, locale, true, "User.updatedEmail").asOkResponse() }
    }
     */
    /**
     * Endpoint for updating user phone
     * @param newPhoneRequest Request object obtained by the request body containing new phone
     * @param user Authenticated user
     * @param locale User locale specified by the headers
     * @return Response given to the update request,
     *         either success or failure if the phone is already found in the database
     * @throws Exception when the id of the authenticated user is not found in the database

    @PostMapping("/update/phone")
    @ApiOperation(value = "Updates user's phone number", response = ApiResponse::class)
    fun updatePhone(
            @ApiParam(required = true, name = "newPhoneRequest", value = "Phone Request Object")
        @Valid @RequestBody newPhoneRequest: UpdatePhoneRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<ApiResponse> {
        val phone = newPhoneRequest.phone
        if (userService.existsByPhone(phone)) {
            return ResponseEntity(
                    ApiResponse.fromMessage(messageSource, locale, false, "Auth.phoneInUse"),
                    HttpStatus.BAD_REQUEST
            )
        }
        return user.user
                .let { userService.updateUser(it.copy(phone = phone)) }
                .let { ApiResponse.fromMessage(messageSource, locale, true, "User.updatedPhone").asOkResponse() }
    }
     */
    /**
     * Endpoint for saving the firebase token for the given user, for sending push notification
     * @param saveFirebaseTokenRequest Request object obtained by the request body containing the firebase token
     * @param user Authenticated user
     * @param locale User locale specified by the headers
     * @return Response given to the save token request
     * @throws Exception when the id of the authenticated user is not found in the database

    @PostMapping("/saveFirebaseToken")
    @ApiOperation(value = "Saves the firebase token and instance id of the user", response = ApiResponse::class)
    fun saveFirebaseToken(
            @ApiParam(
                required = true,
                name = "saveFirebaseTokenRequest",
                value = "Request object containing firebase token and instance it"
        )
        @Valid @RequestBody saveFirebaseTokenRequest: SaveFirebaseTokenRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<ApiResponse> {
        return user.user
                .let {
                    userTokenService.saveFirebaseTokenAndInstanceId(it, saveFirebaseTokenRequest)
                }
                .let { ApiResponse.fromMessage(messageSource, locale, true, "User.savedFirebaseToken").asOkResponse() }
    }

    /**
     * Endpoint for saving a token for the given user
     * @param saveTokenRequest Request object obtained by the request body containing the token and the token tpye
     * @param user Authenticated user
     * @param locale User locale specified by the headers
     * @return Response given to the save token request
     * @throws Exception when the id of the authenticated user is not found in the database
     */
    @PostMapping("/saveToken")
    @ApiOperation(value = "Saves the token of given type for the user", response = ApiResponse::class)
    fun saveToken(
            @ApiParam(
                required = true,
                name = "saveTokenRequest",
                value = "Request object containing the token and token type"
        )
        @Valid @RequestBody saveTokenRequest: SaveTokenRequest,
            @CurrentUser user: UserPrincipal,
            locale: Locale
    ): ResponseEntity<ApiResponse> {
        return user.user
                .let {
                    userTokenService.setTypeForUser(
                            it,
                            saveTokenRequest.tokenType,
                            saveTokenRequest.token
                    )
                }
                .let { ApiResponse.fromMessage(messageSource, locale, true, "User.savedToken").asOkResponse() }
    }

     */


}
