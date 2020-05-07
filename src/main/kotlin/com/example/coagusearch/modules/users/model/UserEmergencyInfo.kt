package com.example.coagusearch.modules.users.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import io.swagger.annotations.ApiModel
import java.time.Instant
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
        name = "user_emergency_info"
)
@ApiModel(description = "Model representing a user's authentication properties")
data class UserEmergencyInfo(
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "doctor_id"
        )
        var doctor: User,

        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "patient_id"
        )
        var patient: User,
        @field:Column(name = "hospital_reach_time")
        var hospitalReachTime: Instant,
        @field:Column(name = "data_ready_time")
        var dataReadyTime: Instant,
        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(UserEmergencyInfo::id)
}
