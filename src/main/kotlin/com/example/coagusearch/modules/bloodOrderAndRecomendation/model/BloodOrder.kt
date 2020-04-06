package com.example.coagusearch.modules.bloodOrderAndRecomendation.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserBloodType
import com.example.coagusearch.modules.users.model.UserRhType
import io.swagger.annotations.ApiModel
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import kotlin.reflect.KProperty1
@Entity
@Table(
        name = "blood_order"
)
@ApiModel(description = "Model representing a blood order")
data class BloodOrder(
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "doctor_id",
                unique = false,
                updatable = false,

                nullable = false
        )
        var doctor: User,


        @field:ManyToOne(fetch = FetchType.LAZY)
        @field:JoinColumn(
                name = "patient_id"
        )
        var patient: User? = null,


        @field:Enumerated(EnumType.STRING)
        @field:Column(name = "blood_type")
        var bloodType: UserBloodType? = null,

        @field:Enumerated(EnumType.STRING)
        @field:Column(name = "rh_type")
        var rhType: UserRhType? = null,

        var unit : Int,

        @field:Column(name = "additional_note")
        var note: String? = null,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(BloodOrder::id)

}
