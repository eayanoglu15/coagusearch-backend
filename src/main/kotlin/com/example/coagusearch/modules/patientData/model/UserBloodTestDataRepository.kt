package com.example.coagusearch.modules.patientData.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserBloodTestDataRepository : JpaRepository<UserBloodTestData, Long> {
    fun findByUserBloodTest(bloodTest: UserBloodTest): List<UserBloodTestData>
}