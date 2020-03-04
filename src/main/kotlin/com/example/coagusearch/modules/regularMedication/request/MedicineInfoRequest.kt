package com.example.coagusearch.modules.regularMedication.request

import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.regularMedication.model.MedicineInfoType
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class MedicineInfoRequest(
        val id: KeyType?,
        val mode: MedicineInfoType,
        val key: String?,
        val uuid: String?,
        val customText: String?,
        val frequency: String?,
        var dosage: Double?
)

