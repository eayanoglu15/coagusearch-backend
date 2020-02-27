package com.example.coagusearch.modules.config

import com.example.coagusearch.security.UserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

@Configuration
@EnableJpaAuditing
class AuditingConfig {
    @Bean
    fun auditorProvider(): AuditorAware<Long> {
        return SpringSecurityAuditAwareImpl()
    }
}

private class SpringSecurityAuditAwareImpl : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated || auth is AnonymousAuthenticationToken) {
            return Optional.empty()
        }
        val principal = auth.principal as UserPrincipal
        return Optional.of(principal.id)
    }
}
