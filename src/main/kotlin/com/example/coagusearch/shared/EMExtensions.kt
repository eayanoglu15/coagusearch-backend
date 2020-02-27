package com.example.coagusearch.shared

import com.example.coagusearch.modules.base.model.DbEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.orm.ObjectOptimisticLockingFailureException
import javax.persistence.EntityManager

inline fun <reified T : DbEntity> EntityManager.asReference(obj: T): T =
    this.getReference(T::class.java, obj.id)

val logger: Logger = LoggerFactory.getLogger("EMExtensions")

inline fun <T> retriedOnOptimisticLockingException(times: Int, fn: () -> T): T {
    var i = 0
    var lastEx: ObjectOptimisticLockingFailureException? = null
    while (i < times) {
        try {
            val res = fn()
            if (i != 0) {
                logger.info("Retried $i times because of optimistic locking exceptions")
            }
            return res
        } catch (ex: ObjectOptimisticLockingFailureException) {
            i++
            lastEx = ex
        }
    }
    logger.info("Retried $i times because of optimistic locking exceptions")
    throw lastEx!!
}
