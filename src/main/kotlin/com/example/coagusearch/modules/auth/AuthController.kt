package com.example.coagusearch.modules.auth

import com.example.coagusearch.modules.auth.request.LoginRequest
import com.example.coagusearch.modules.auth.request.RefreshRequest
import com.example.coagusearch.modules.auth.request.UserSaveRequest
import com.example.coagusearch.modules.auth.response.AuthResponse
import com.example.coagusearch.modules.auth.response.JwtAuthenticationResponse
import com.example.coagusearch.modules.auth.response.JwtRefreshResponse
import com.example.coagusearch.modules.auth.response.UserSaveResponse
import com.example.coagusearch.modules.auth.service.AuthService
import com.example.coagusearch.modules.base.BaseController
import com.example.coagusearch.modules.users.service.UserService
import com.example.coagusearch.security.CurrentUser
import com.example.coagusearch.security.JwtTokenProvider
import com.example.coagusearch.security.UserPrincipal
import com.example.coagusearch.shared.RestException
import com.example.coagusearch.shared.asOkResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * Endpoint for authentication related things
 */
@RestController
@RequestMapping("/auth")
@Validated
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Users.")
class AuthController @Autowired constructor(
        val authenticationManager: AuthenticationManager,
        val authService: AuthService,
        val userService: UserService,
        val tokenProvider: JwtTokenProvider,
        val messageSource: MessageSource
) : BaseController() {
    /**
     * Endpoint for logging in
     * @param loginRequest Login request containing email and password
     * @return Response containing jwt auth and refresh token if logged in successfully
     */
    @PostMapping("/sign-in")
    @ApiOperation(value = "Log in an user", response = JwtAuthenticationResponse::class)
    fun authenticateUser(
            @ApiParam(required = true, name = "loginRequest", value = "Login Request Object")
        @Valid @RequestBody loginRequest: LoginRequest,
            httpServletRequest: HttpServletRequest
    ): ResponseEntity<JwtAuthenticationResponse> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.identity_number,
                        loginRequest.password
                )
        )
        val principle = authentication.principal as UserPrincipal

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.generateToken(authentication)

        val user = principle.user
        authService.saveRefreshToken(user, jwt.refreshToken)
        /* userTokenService.setAllForUser(
                user,
                httpServletRequest.headersToTokens()
        ) */
        return ResponseEntity.ok(jwt)
    }

    @PostMapping("/tempSignIn")
    fun saveAUser(
            @Valid @RequestBody userSaveRequest: UserSaveRequest,
            locale: Locale
    ): ResponseEntity<UserSaveResponse> {
        return authService.signInUserTemp(userSaveRequest).asOkResponse()
    }

    @PostMapping("/savePatient")
    fun savePatient(
            @Valid @RequestBody userSaveRequest: UserSaveRequest,
            @CurrentUser userPrincipal: UserPrincipal,
            locale: Locale
    ): ResponseEntity<UserSaveResponse> {
        val user = userPrincipal.user
        return authService.signInPatient(user,userSaveRequest).asOkResponse()
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "Refreshes the user access token")
    fun refreshToken(
            @Valid @RequestBody refreshRequest: RefreshRequest,
            httpServletRequest: HttpServletRequest
    ): ResponseEntity<JwtRefreshResponse> {
        if (!tokenProvider.validateToken(refreshRequest.refreshToken)) {
            throw RestException("RefreshToken.invalid", HttpStatus.UNAUTHORIZED).apply {
                hiddenMessage = "Given token is not valid"
            }
        }
        val userId = tokenProvider.getUserIdFromJWT(refreshRequest.refreshToken)
        val user = authService.getUserById(userId)
        val isTokenValid = authService.validateRefreshToken(user, refreshRequest.refreshToken)
        if (!isTokenValid) {
            throw RestException("RefreshToken.invalid", HttpStatus.UNAUTHORIZED).apply {
                hiddenMessage =
                        "Given token is not valid for the user, user: $userId, token: ${refreshRequest.refreshToken}"
            }
        }
        val jwt = tokenProvider.regenerateToken(userId)
        return ResponseEntity.ok(jwt)
    }




/*

    /**
     * Endpoints for registering a new user
     * @param signUpRequest Request containing immediate user details in the beginning of the registration
     * @param locale Locale from http headers
     * @return Response entity containing the registration success status
     */

    @PostMapping("/sign-up")
    @ApiOperation(value = "Signs up an user", response = AuthResponse::class)
    @ApiImplicitParams(
            ApiImplicitParam(
                    name = "X-Timezone",
                    value = "Timezone header for the user, required format is Europe/Istanbul",
                    required = false
            ),
            ApiImplicitParam(
                    name = "X-OS",
                    value = "User operating system, required format is Android | IOS",
                    required = false
            )
    )
    @Suppress("ReturnCount")
    fun registerUser(
            @ApiParam(required = true, name = "signUpRequest", value = "SignUp Request Object")
        @Valid @RequestBody signUpRequest: SignUpRequest,
            @RequestHeader(value = "X-Timezone", required = false) timezone: String?,
            @RequestHeader(value = "X-OS", required = false) os: String?,
            locale: Locale,
            httpServletRequest: HttpServletRequest
    ): ResponseEntity<AuthResponse> {
        // Check for unique email and phone
        if (authService.existsByEmail(signUpRequest.email)) {
            return ResponseEntity(
                    AuthResponse.fromMessage(messageSource, locale, false, "Auth.emailInUse"),
                    HttpStatus.BAD_REQUEST
            )
        }

        if (authService.existsByPhone(signUpRequest.phone)) {
            return ResponseEntity(
                    AuthResponse.fromMessage(messageSource, locale, false, "Auth.phoneInUse"),
                    HttpStatus.BAD_REQUEST
            )
        }

        // Encode password


        val user = User(
                firstName = signUpRequest.firstName,
                surname = signUpRequest.surname,
                email = signUpRequest.email,
                password = password,
                phone = signUpRequest.phone,
                sex = signUpRequest.sex,
                userRoles = EnumSet.of(UserRole.USER),
                identifier = UUID.randomUUID().toString()
        ).let {
            authService.createUser(it)
        }
        if (signUpRequest.weight != null && signUpRequest.height != null && signUpRequest.birthDay != null) {
            UserBodyInfo(
                    weight = signUpRequest.weight.value,
                    weightUnit = signUpRequest.weight.unit,
                    height = signUpRequest.height.value,
                    heightUnit = signUpRequest.height.unit,
                    user = user,
                    answeredAt = DateTimeZone.UTC.getCurrentTime(),
                    active = true,
                    birthDay = LocalDateTime.ofEpochSecond(signUpRequest.birthDay, 0, ZoneOffset.UTC
                    ).toInstant(ZoneOffset.UTC)
            ).let {
                bodyInfoRepository.save(it)
            }
        }
        userService.putUserTimeZone(user, locale, timezone ?: locale.toLanguage().defaultTimeZone)
        userService.putUserOperatingSystem(user, os ?: "Unknown")

        userTokenService.setAllForUser(
                user,
                httpServletRequest.headersToTokens()
        )

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/me")
                .buildAndExpand().toUri()

        return ResponseEntity.created(location).body(
                AuthResponse.fromMessage(messageSource, locale, true, "Auth.registered")
        )
    }

 */
    /*
/**
 * Endpoint for refreshing an auth token using the access token
 * @param refreshRequest Request object containing refresh token
 * @return Response containing new auth token
 */


/**
 * Endpoint for logging out a user by revoking their refresh token
 * @param user Current authenticated user
 * @param locale Current user locale
 * @return Whether or not successfully revoked the auth token
 */
@DeleteMapping("/revoke")
@ApiOperation(value = "Revoke refresh token for the user")
fun revokeToken(@CurrentUser user: UserPrincipal, locale: Locale): ResponseEntity<AuthResponse> {
    val userDao = user.user
    authService.revokeRefreshToken(userDao)
    return ResponseEntity.ok(AuthResponse.fromMessage(messageSource, locale, true, "Auth.revoked"))
}
*/
}
