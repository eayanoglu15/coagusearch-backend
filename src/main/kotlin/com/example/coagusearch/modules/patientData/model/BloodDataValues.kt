package com.example.coagusearch.modules.patientData.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import io.swagger.annotations.ApiModel
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "blood_data_values", uniqueConstraints = [
    UniqueConstraint(columnNames = ["key"])
]
)
@ApiModel(description = "Model representing a blood data values")
data class BloodDataValues(
        var key: String,
        var minValue: Double,
        var maxValue: Double,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(BloodDataValues::id)
}

