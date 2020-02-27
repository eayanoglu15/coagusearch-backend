package com.example.coagusearch.security

import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserCaseType
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
        val user: User,
        val id: Long,
        var identityNumber:String,
        type: UserCaseType,
        password: String,
        authorities: MutableCollection<GrantedAuthority>
) : UserDetails {
    companion object {
        fun create(user: User): UserPrincipal {
            val authorities: MutableCollection<GrantedAuthority> = mutableListOf(SimpleGrantedAuthority("USER"))
            return UserPrincipal(
                user,
                user.id!!,
                user.identityNumber,
                user.type,
                user.password,
                authorities
            )
        }
    }

    private val usernameInternal: String = username

    @JsonIgnore
    val passwordInternal: String = password

    private val authoritiesInternal: MutableCollection<GrantedAuthority> = authorities

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = this.authoritiesInternal

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = this.usernameInternal

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = this.passwordInternal

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPrincipal

        if (user.identityNumber != other.user.identityNumber) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
