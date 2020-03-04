package com.example.coagusearch.modules.regularMedication.model

import com.example.coagusearch.modules.base.model.KeyType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DrugFrequencyRepository : JpaRepository<DrugFrequency, KeyType> {
    override fun findAll() : List<DrugFrequency>
    fun findByKey(key:String?) : DrugFrequency?
}