package com.example.coagusearch.modules.users.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.shared.audit.DateAudit
import io.swagger.annotations.ApiModel
import org.hibernate.annotations.NaturalId
import java.time.LocalDateTime
import java.util.EnumSet
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.reflect.KProperty1

@Entity
@Table(
    name = "Users", uniqueConstraints = [
        UniqueConstraint(columnNames = ["identity_number"])
    ]
)
@ApiModel(description = "Model representing a user's authentication properties")
data class User(
        @field:NotBlank
        @field:Column(name = "identity_number", nullable = false)
        var identityNumber: String,

        @field:NotBlank
        var password: String,

        @field:Enumerated(EnumType.STRING)
        var type: UserCaseType,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
        listOf(User::identityNumber)

   // fun hasRole(role: UserRole): Boolean = userRoles.contains(role)
}

enum class UserCaseType {
    Doctor,
    Medical,
    Patient
}
