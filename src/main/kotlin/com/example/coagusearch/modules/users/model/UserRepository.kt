package com.example.coagusearch.modules.users.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByIdentityNumber(email: String): Optional<User>
    fun existsByIdentityNumber(email: String): Boolean
}
