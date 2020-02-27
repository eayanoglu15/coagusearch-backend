package com.example.coagusearch.modules.auth.service

import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.security.UserPrincipal
import org.springframework.security.core.Authentication

interface IAuthenticationFacade : IUserFacade {
    val authentication: Authentication?
    val principal: UserPrincipal?
}

interface IUserFacade {
    val user: User?
}

typealias UserSupplier = () -> User?

fun IUserFacade.toSupplier(): UserSupplier = { user }
