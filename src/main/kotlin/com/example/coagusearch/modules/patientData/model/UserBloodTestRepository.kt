package com.example.coagusearch.modules.patientData.model

import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserBloodTestRepository : JpaRepository<UserBloodTest, Long> {
    fun findAllByUser(user: User): List<UserBloodTest>
    fun findFirstByUserOrderByTestedAtDesc(user:User): UserBloodTest?

}