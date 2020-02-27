package com.example.coagusearch.modules.base.model

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "shedlock")
class Shedlock(
    @field:Id
    @field:Column(name = "name", length = 64, nullable = false)
    var name: String,

    @field:Column(name = "lock_until", length = 3, nullable = true)
    var lockUntil: Timestamp,

    @field:Column(name = "locked_at", length = 3, nullable = true)
    var lockedAt: Timestamp,

    @field:Column(name = "locked_by", length = 255, nullable = false)
    var lockedBy: String
)
