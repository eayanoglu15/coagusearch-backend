package com.example.coagusearch.modules.patientData.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BloodTestsRepository : JpaRepository<BloodTests, Long> {

}
