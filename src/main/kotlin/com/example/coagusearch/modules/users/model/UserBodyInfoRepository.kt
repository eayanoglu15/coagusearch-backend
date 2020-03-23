package com.example.coagusearch.modules.users.model

import com.example.coagusearch.modules.base.model.KeyType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserBodyInfoRepository : JpaRepository<UserBodyInfo, Long> {
    fun findFirstByUserOrderByIdDesc(user: User): UserBodyInfo?
}
