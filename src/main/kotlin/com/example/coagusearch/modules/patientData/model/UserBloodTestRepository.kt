package com.example.coagusearch.modules.patientData.model

import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface UserBloodTestRepository : JpaRepository<UserBloodTest, Long> {
    fun findAllByUserAndTestedAtLessThanEqual(user: User,testedAt:Instant): List<UserBloodTest>
    fun findFirstByUserAndTestedAtLessThanEqualOrderByTestedAtDesc(user:User, testedAt: Instant): UserBloodTest?



}