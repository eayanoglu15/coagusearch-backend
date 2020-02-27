package com.example.coagusearch.modules.auth.service

import com.example.coagusearch.modules.users.model.UserRepository
import com.example.coagusearch.security.UserPrincipal
import com.example.coagusearch.shared.requireNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsService @Autowired constructor(
    val userRepository: UserRepository
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(email: String?): UserDetails {
        email.requireNotNull { UsernameNotFoundException("null") }
        val user = userRepository.findByIdentityNumber(email)
            .orElseThrow { UsernameNotFoundException("$email") }
        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id)
            .orElseThrow { UsernameNotFoundException("$id") }
        return UserPrincipal.create(user)
    }
}
