package com.example.coagusearch.modules.auth.service

import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.security.UserPrincipal
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade : IAuthenticationFacade {
    override val principal: UserPrincipal?
        get() = authentication?.principal as? UserPrincipal?
    override val authentication: Authentication?
        get() = getAuthenticationSafe()
    override val user: User?
        get() = principal?.user

    private fun getAuthenticationSafe(): Authentication? {
        val auth: Authentication? = SecurityContextHolder.getContext().authentication
        return if (auth != null && auth !is AnonymousAuthenticationToken) {
            auth
        } else {
            null
        }
    }
}
