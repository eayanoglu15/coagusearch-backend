package com.example.coagusearch.modules.regularMedication.model

import com.example.coagusearch.modules.base.model.KeyType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface DrugInfoRepository : JpaRepository<DrugInfo, KeyType>{
    override fun findAll() : List<DrugInfo>
    fun findByKey(key:String?) : DrugInfo?
}
