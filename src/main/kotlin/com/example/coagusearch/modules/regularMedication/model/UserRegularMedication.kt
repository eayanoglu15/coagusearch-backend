package com.example.coagusearch.modules.regularMedication.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "user_regular_medicine"
)
data class UserRegularMedication(
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(name = "user_id", nullable = false, updatable = false)
        var user: User,

        @field:Enumerated(EnumType.STRING)
        @field:Column(name = "mode", nullable = false)
        var mode: MedicineInfoType,

        @field:ManyToOne(fetch = FetchType.LAZY)
        @field:JoinColumn(name = "drug_key", nullable = true, updatable = false)
        var drug: DrugInfo?,

        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(name = "frequency", nullable = false, updatable = false)
        var frequency: DrugFrequency?,

        @field:Column(name = "dosage", nullable = true)
        var dosage: Double?,

        @field:Column(name = "custom", nullable = true, length = 1024)
        var custom: String?,

        @field:Column(name = "active", nullable = false)
        var active: Boolean = true,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(UserRegularMedication::id)
}

enum class MedicineInfoType {
    KEY,
    CUSTOM
}

