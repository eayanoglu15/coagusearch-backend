package com.example.coagusearch.modules.regularMedication.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.shared.MultiLanguageString
import io.swagger.annotations.ApiModel
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotBlank
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "drug_frequency", uniqueConstraints = [
    UniqueConstraint(columnNames = ["key"])
]
)
@ApiModel(description = "Model representing a drug info")
data class DrugFrequency(
        @field:NotBlank
        @field:Column(name = "key", nullable = false)
        var key: String,

        @field:OneToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "detail",
                unique = true,
                updatable = false,

                nullable = false
        )
        var detail: MultiLanguageString,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(DrugFrequency::key)


}
