package com.example.coagusearch.modules.users.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.shared.audit.DateAudit
import io.swagger.annotations.ApiModel
import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "User_body_info", uniqueConstraints = [
    UniqueConstraint(columnNames = ["user_id"]),
    UniqueConstraint(columnNames = ["id"])
]
)
@ApiModel(description = "Model representing a user's authentication properties")
data class UserBodyInfo(
        @field:OneToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "user_id",
                unique = true,
                updatable = false,

                nullable = false
        )
        var user: User,
        var name: String,
        var surname: String,

        @field:Column(name = "date_of_birth")
        var dateOfBirth: Instant? = null,
        var height: Double? = null,
        var weight: Double? = null,

        @field:Column(name = "blood_type")
        var bloodType: UserBloodType? = null,

        @field:Column(name = "rh_type")
        var rhType: UserRhType? = null,

        var gender: UserGender? = null,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DateAudit() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(UserBodyInfo::id)
}

enum class UserGender {
Male,Female
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

