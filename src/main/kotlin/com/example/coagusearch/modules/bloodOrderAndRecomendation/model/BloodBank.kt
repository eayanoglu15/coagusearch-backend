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
        @field:Column(name = "ffp_0pos")
        var ffp_0pos:Int? = null,

        @field:Column(name = "ffp_0neg")
        var ffp_0neg: Int? = null,

        @field:Column(name = "ffp_Apos")
        var ffp_Apos: Int? = null,

        @field:Column(name = "ffp_Aneg")
        var ffp_Aneg: Int? = null,

        @field:Column(name = "ffp_Bpos")
        var ffp_Bpos: Int? = null,

        @field:Column(name = "ffp_Bneg")
        var ffp_Bneg: Int? = null,

        @field:Column(name = "ffp_ABpos")
        var ffp_ABpos: Int? = null,

        @field:Column(name = "ffp_ABneg")
        var ffp_ABneg: Int? = null,

        @field:Column(name = "pc_0pos")
        var pc_0pos: Int? = null,

        @field:Column(name = "pc_0neg")
        var pc_0neg: Int? = null,

        @field:Column(name = "pc_Apos")
        var pc_Apos: Int? = null,

        @field:Column(name = "pc_Aneg")
        var pc_Aneg: Int? = null,

        @field:Column(name = "pc_Bpos")
        var pc_Bpos: Int? = null,

        @field:Column(name = "pc_Bneg")
        var pc_Bneg: Int? = null,

        @field:Column(name = "pc_ABpos")
        var pc_ABpos: Int? = null,

        @field:Column(name = "pc_ABneg")
        var pc_ABneg: Int? = null,
        var bloodbankStorage : HashMap<String, Int>
        = HashMap<String, Int> (),


        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(BloodOrder::id)


        fun bloodbankInitilize() {
                bloodbankStorage.put("ffp_0pos",ffp_0pos!!)
                bloodbankStorage.put("ffp_0neg",ffp_0neg!!)
                bloodbankStorage.put("ffp_Apos",ffp_Apos!!)
                bloodbankStorage.put("ffp_Aneg",ffp_Aneg!!)
                bloodbankStorage.put("ffp_Bpos",ffp_Bpos!!)
                bloodbankStorage.put("ffp_Bneg",ffp_Bneg!!)
                bloodbankStorage.put("ffp_ABpos",ffp_ABpos!!)
                bloodbankStorage.put("ffp_ABneg",ffp_ABneg!!)
                bloodbankStorage.put("pc_0pos",pc_0pos!!)
                bloodbankStorage.put("pc_0neg",pc_0neg!!)
                bloodbankStorage.put("pc_Apos",pc_Apos!!)
                bloodbankStorage.put("pc_Aneg",pc_Aneg!!)
                bloodbankStorage.put("pc_Bpos",pc_Bpos!!)
                bloodbankStorage.put("pc_Bneg",pc_Bneg!!)
                bloodbankStorage.put("pc_ABpos",pc_ABpos!!)
                bloodbankStorage.put("pc_ABneg",pc_ABneg!!)
        }






}


