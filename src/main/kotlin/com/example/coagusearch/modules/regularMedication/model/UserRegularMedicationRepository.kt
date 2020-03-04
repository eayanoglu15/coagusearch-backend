package com.example.coagusearch.modules.regularMedication.model

import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository




@Repository
interface UserRegularMedicationRepository : JpaRepository<UserRegularMedication, KeyType> {
    fun findAllByUser(user: User) : List<UserRegularMedication>
}
