package com.example.coagusearch.modules.users.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import io.swagger.annotations.ApiModel
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.reflect.KProperty1


@Entity
@Table(
        name = "doctor_medical_relationship", uniqueConstraints = [
    UniqueConstraint(columnNames = ["doctor_id","medical_id"])
]
)
@ApiModel(description = "Model representing relationship between doctor and medical" )
data class UserDoctorMedicalRelationship(
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "doctor_id",
                unique = false,
                updatable = false,

                nullable = false
        )
        var doctor: User,

        @field:OneToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "medical_id",
                unique = true,
                updatable = false,
                nullable = false
        )
        var medical: User,

        var active: Boolean = true,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(UserDoctorMedicalRelationship::id)
}
