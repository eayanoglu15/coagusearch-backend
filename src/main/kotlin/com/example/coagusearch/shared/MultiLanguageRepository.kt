package com.example.coagusearch.shared

import com.example.coagusearch.modules.notification.model.Notification
import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Repository
interface MultiLanguageRepository : JpaRepository<MultiLanguageString, Long> {

}
