package com.example.coagusearch.shared.audit

import com.example.coagusearch.modules.base.model.DbEntity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

/**
 * Audit type for adding created and updated dates to a jpa entity
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
abstract class DateAudit : DbEntity() {
    /**
     * Record creation time, cannot be changed
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: Instant? = null

    /**
     * Last update time of the record
     */
    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: Instant? = null
}
