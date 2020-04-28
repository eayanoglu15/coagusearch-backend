package com.example.coagusearch.modules.patientData.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
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
import javax.persistence.UniqueConstraint
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "blood_tests", uniqueConstraints = [
    UniqueConstraint(columnNames = ["test_name","category_name"])
]
)
@ApiModel(description = "Model representing a user's authentication properties")
data class BloodTests(
        @field:Enumerated(EnumType.STRING)
        @field:Column(name = "test_name", nullable = false)
        var testName: BloodTestName,

        @field:Enumerated(EnumType.STRING)
        @field:Column(name = "category_name", nullable = false)
        var categoryName: BloodCategoryName,
        @field:Column(name = "optimum_maximum", nullable = false)
        var optimumMaximum: Double,
        @field:Column(name = "optimum_minimum", nullable = false)
        var optimumMinimum: Double,
        @field:Column(name = "maximum", nullable = false)
        var maximum: Double,
        @field:Column(name = "minimum", nullable = false)
        var minimum: Double,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(BloodTests::id)
}

enum class BloodCategoryName {
    CT,
    CFT,
    Alpha,
    A10,
    A20,
    MCF,
    LI30
}

enum class BloodTestName {
    fibtem,
    extem,
    intem
}

