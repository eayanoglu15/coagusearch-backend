package com.example.coagusearch.modules.regularMedication.response

import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.regularMedication.model.MedicineInfoType

data class UserMedicineResponse(
        val allDrugs: AllDrugInfoResponse,
        val userDrugs: List<MedicineInfoResponse>
)
data class MedicineInfoResponse(
        val id: KeyType,
        val mode: MedicineInfoType,
        val custom: String?,
        val key: String?,
        //val content: String?,
        val frequency: DrugFrequencyResponse?,
        val dosage: Double?
)
data class DrugFrequencyResponse(
        val key: String,
        val title: String
)
