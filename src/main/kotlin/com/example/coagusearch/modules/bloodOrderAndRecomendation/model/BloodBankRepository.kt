package com.example.coagusearch.modules.bloodOrderAndRecomendation.model

import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.patientData.model.UserBloodTest
import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository


interface BloodBankRepository : JpaRepository<BloodBank, Long> {
    fun findByKey(key: String) : BloodBank?
    fun deleteById(id: KeyType?)
}
