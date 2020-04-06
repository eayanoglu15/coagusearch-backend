package com.example.coagusearch.modules.auth.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.shared.audit.DateAudit
import org.hibernate.annotations.NaturalId
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "RefreshToken"
)
data class RefreshToken(
        @field:OneToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "user_id",
                unique = true,
                updatable = false,

                nullable = false
        )
        @field:NaturalId
        var user: User,

        var refreshToken: String?,

        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DateAudit() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(RefreshToken::user)
}
