package com.example.coagusearch.shared

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.base.model.Language
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
        name = "multi_language_string", uniqueConstraints = [
    UniqueConstraint(columnNames = ["key"]),
    UniqueConstraint(columnNames = ["id"])
]
)
@ApiModel(description = "Model representing a drug info")
data class MultiLanguageString(
        @field:NotBlank
        @field:Column(name = "key", nullable = false)
        var key: String,

        var tr_string: String,
        var en_string: String,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(MultiLanguageString::key)
    fun stringByLanguage(language:Language):String{
            if(language == Language.EN)
                return en_string
            return tr_string
    }
}
