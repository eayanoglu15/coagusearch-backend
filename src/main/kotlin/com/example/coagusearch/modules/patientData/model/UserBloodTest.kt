package com.example.coagusearch.modules.patientData.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
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
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "user_blood_test", uniqueConstraints = [
    UniqueConstraint(columnNames = ["user_id", "tested_at"])
]
)
@ApiModel(description = "Model representing a user's authentication properties")
data class UserBloodTest(

        @field:Column(name = "tested_at", nullable = false)
        var testedAt: Instant,


        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "user_id",
                unique = false,
                updatable = false,

                nullable = false
        )
        var user: User,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(BloodTests::id)
}
