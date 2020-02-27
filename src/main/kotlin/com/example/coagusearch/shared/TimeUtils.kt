package com.example.coagusearch.shared

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Converts the given time instant to string using the specified format and timezone
 * @param format Required format of the instant, as a DateTimeFormatter pattern
 * @param timezone Timezone of the instant, default is UTC
 * @return The instant converted to string
 */
fun Instant.formatWith(format: String, timezone: ZoneOffset = ZoneOffset.UTC): String {
    val dateTime = LocalDateTime.ofInstant(this, timezone)
    return DateTimeFormatter.ofPattern(format).format(dateTime)
}

fun String.formatInstantFrom(format: String): Instant {
    return DateTimeFormat.forPattern(format).parseDateTime(this).toDate().toInstant()
}

fun DateTime.getTodayInUtc(): Pair<Instant, Instant> {
    val startInstant = this.withTimeAtStartOfDay().toDate().toInstant()
    val endInstant = this.plusDays(1).withTimeAtStartOfDay().toDate().toInstant()
    return startInstant to endInstant
}

fun DateTimeZone.getTodayInUtc(): Pair<Instant, Instant> {
    val time = DateTime.now(this)
    return time.getTodayInUtc()
}

fun DateTimeZone.getCurrentTime(): Instant =
    DateTime.now(this).toDate().toInstant()
