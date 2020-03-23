package com.example.coagusearch.modules.users.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.shared.audit.DateAudit
import io.swagger.annotations.ApiModel
import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "user_body_info"
)
@ApiModel(description = "Model representing a user's authentication properties")
data class UserBodyInfo(
        @field:OneToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "user_id",
                nullable = false
        )
        var user: User,

        var name: String,
        var surname: String,

        @field:Column(name = "date_of_birth")
        var dateOfBirth: Instant? = null,
        var height: Double? = null,
        var weight: Double? = null,

        @field:Enumerated(EnumType.STRING)
        @field:Column(name = "blood_type")
        var bloodType: UserBloodType? = null,

        @field:Enumerated(EnumType.STRING)
        @field:Column(name = "rh_type")
        var rhType: UserRhType? = null,

        @field:Enumerated(EnumType.STRING)
        var gender: UserGender? = null,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(UserBodyInfo::id)

    fun isMissing(): Boolean {
        return dateOfBirth == null || height == null || weight == null
                || bloodType == null || rhType == null || gender == null


    }
}

enum class UserGender {
    Male, Female
}

enum class UserRhType {
    Positive,
    Negative
}

enum class UserBloodType {
    A,
    B,
    AB,
    O
}

