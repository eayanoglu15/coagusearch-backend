package com.example.coagusearch.modules.auth.model

import com.example.coagusearch.modules.auth.model.RefreshToken
import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun existsByUser(user: User): Boolean

    fun findByUser(user: User): Optional<RefreshToken>
}
