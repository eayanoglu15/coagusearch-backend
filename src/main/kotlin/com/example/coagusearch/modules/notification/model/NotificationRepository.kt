package com.example.coagusearch.modules.notification.model

import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findAllByUser(user: User): List<Notification>
    fun findAllByUserAndAddedAtLessThanEqual(user: User, now: Instant = LocalDateTime.now(ZoneId.of("Turkey")).toInstant(ZoneId.of("Europe/Istanbul").getRules().getOffset(Instant.now()))
    ): List<Notification>
}
