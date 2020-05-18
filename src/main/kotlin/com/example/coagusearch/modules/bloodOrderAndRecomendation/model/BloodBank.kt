package com.example.coagusearch.modules.bloodOrderAndRecomendation.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.patientData.model.UserBloodTest
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserBloodType
import com.example.coagusearch.modules.users.model.UserRhType
import io.swagger.annotations.ApiModel
import java.time.LocalDateTime
import java.time.LocalTime
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
        name = "bloodbank"
)
@ApiModel(description = "Model representing a blood order")
data class BloodBank(
        @field:Column(name = "key")
        var key: String,
        @field:Column(name = "value")
        var value: Int,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(BloodOrder::id)
    companion object {
        var keyList = listOf("ffp_Opos", "ffp_Oneg", "ffp_Apos", "ffp_Aneg",
                "ffp_Bpos", "ffp_Bneg", "ffp_ABpos", "ffp_ABneg", "pc_Opos", "pc_Oneg", "pc_Apos",
                "pc_Aneg", "pc_Bpos", "pc_Bneg", "pc_ABpos", "pc_ABneg")
    }
}


