package com.example.coagusearch.modules.notification.model

import com.example.coagusearch.modules.base.model.DbEntity
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.shared.MultiLanguageString
import io.swagger.annotations.ApiModel
import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import kotlin.reflect.KProperty1

@Entity
@Table(
        name = "notification"
)
@ApiModel(description = "Model representing a user's authentication properties")
data class Notification(
        @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(
                name = "user_id",
                nullable = false
        )
        var user: User,
        @field:Column(name = "notification_string")
        var notificationString: MultiLanguageString,
        @field:Column(name = "added_at")
        var addedAt: Instant,
        @field:Column(name = "active")
        var active: Boolean = true,
        override var id: KeyType? = null,
        override var version: LocalDateTime? = null
) : DbEntity() {
    override fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
            listOf(Notification::id)

}
