package com.example.coagusearch.modules.patientData.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import io.swagger.annotations.ApiModel
import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.reflect.KProperty1


@Entity
@Table(
        name = "user_blood_test_data", uniqueConstraints = [
    UniqueConstraint(columnNames = ["user_blood_test","blood_test"])
]
)
@ApiModel(description = "Model representing a user's authentication properties")
data class UserBloodTestData(
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "user_blood_test",
                unique = false,
                updatable = false,

                nullable = false
        )
        var userBloodTest: UserBloodTest,
        var value: Double? = null,
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "blood_test",
                unique = false,
                updatable = false,

                nullable = false
        )
        var bloodTest: BloodTests,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(BloodTests::id)
}
