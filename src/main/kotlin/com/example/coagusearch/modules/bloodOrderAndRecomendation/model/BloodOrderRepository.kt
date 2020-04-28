package com.example.coagusearch.modules.bloodOrderAndRecomendation.model

import com.example.coagusearch.modules.patientData.model.UserBloodTest
import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository


interface BloodOrderRepository : JpaRepository<BloodOrder, Long> {
    fun findAllByPatient(patient: User): List<BloodOrder>
    fun findAllByDoctor(doctor: User): List<BloodOrder>
    fun findAllByBloodTest(bloodTest: UserBloodTest): List<BloodOrder>
}
