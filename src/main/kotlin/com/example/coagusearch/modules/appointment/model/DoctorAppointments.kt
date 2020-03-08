package com.example.coagusearch.modules.appointment.model

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
        name = "doctor_appointments", uniqueConstraints = [
    UniqueConstraint(columnNames = ["id"])
]
)
@ApiModel(description = "Model representing a user's authentication properties")
data class DoctorAppointments(
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "doctor_id",
                unique = false,
                updatable = false,

                nullable = false
        )
        var doctor: User,

        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "patient_id",
                unique = false,
                updatable = false,

                nullable = false
        )
        var patient: User,
        var day: Int,
        var month: Int,
        var year: Int,
        var hour:Int,
        var minute:Int,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(DoctorAppointments::id)
}

