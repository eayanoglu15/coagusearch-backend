package com.example.coagusearch.security

import com.example.coagusearch.modules.auth.service.CustomUserDetailsService
import com.example.coagusearch.shared.requireNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
class WebSecurityConfig @Autowired constructor(
        private val customUserDetailsService: CustomUserDetailsService,
        private val unauthorizedHandler: JwtAuthenticationEntryPoint,
        private val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http.requireNotNull { RuntimeException("Http should not be null in configure") }

        http.cors().and().csrf().disable()

        http.authorizeRequests()
                .antMatchers("/", "/csrf").permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        "/auth/sign-in",
                        "/auth/sign-up",
                        "/auth/tempSignIn",
                        "/blood/resetBloodbank",
                        "/auth/refresh"
                ).permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/v2/api-docs",
                        "/blood/bloodbank",
                        "/blood/bloodreq",
                        "/configuration/ui",
                        "/swagger-resources",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/resources/**",
                        "/error"
                ).permitAll()
                .antMatchers("/actuator", "/actuator/**").permitAll()
                .anyRequest().authenticated()

        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth.requireNotNull { RuntimeException("Auth should not be null in configure") }
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder())
    }

    override fun configure(web: WebSecurity?) {
        web.requireNotNull { RuntimeException("Web should not be null in configure") }
        web.ignoring()
                .antMatchers(
                        "/favicon.ico",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources",
                        "/configuration/security",
                       "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/resources/**",
                        "/error"
                )
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()
}
