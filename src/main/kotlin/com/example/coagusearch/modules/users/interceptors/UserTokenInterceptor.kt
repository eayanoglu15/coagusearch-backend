package com.example.coagusearch.modules.users.interceptors

import com.example.coagusearch.security.UserPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserTokenInterceptor(
    //private val userTokenService: UserTokenService
) : HandlerInterceptorAdapter() {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val auth =
            SecurityContextHolder.getContext().authentication ?: return true
        val principal = (auth as? UsernamePasswordAuthenticationToken)?.principal as? UserPrincipal ?: return true
        val user = principal.user
       // val tokens = request.headersToTokens()
       // userTokenService.setAllForUser(user, tokens)
        return true
    }
}
