package com.example.coagusearch.modules.users.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserBodyInfoRepository : JpaRepository<UserBodyInfo, Long> {
    fun findFirstByUserOrderByIdDesc(user: User): UserBodyInfo?
}
