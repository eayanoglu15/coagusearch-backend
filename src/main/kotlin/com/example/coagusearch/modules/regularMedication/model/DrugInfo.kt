package com.example.coagusearch.modules.regularMedication.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import io.swagger.annotations.ApiModel
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotBlank
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "drug_info", uniqueConstraints = [
    UniqueConstraint(columnNames = ["key"]),
    UniqueConstraint(columnNames = ["id"])
]
)
@ApiModel(description = "Model representing a drug info")
data class DrugInfo(
        @field:NotBlank
        @field:Column(name = "key", nullable = false)
        var key: String,

        @field:Column(name = "name", nullable = false)
        var name: String,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(DrugInfo::key)
}
