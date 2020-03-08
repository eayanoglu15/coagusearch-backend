package com.example.coagusearch.modules.patientData.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import io.swagger.annotations.ApiModel
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.reflect.KProperty1
@Entity
@Table(
        name = "patient_data", uniqueConstraints = [
    UniqueConstraint(columnNames = ["id"])
]
)
@ApiModel(description = "Model representing a patient base blood data")
data class PatientData(
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "patient_id",
                unique = false,
                updatable = false,

                nullable = false
        )
        var patient: User,
        var bloodDataValues: BloodDataValues,
        var value: Double,
        var createdAt: LocalDateTime? = null,
        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(PatientData::id)
}

