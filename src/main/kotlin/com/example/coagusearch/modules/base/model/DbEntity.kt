package com.example.coagusearch.modules.base.model

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Version
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties

/**
 * Base class for all JPA entities with id and version fields that are not used for object equality.
 */
@Access(AccessType.FIELD)
@MappedSuperclass
abstract class DbEntity : Serializable {

    @get:[Access(AccessType.PROPERTY) Id GeneratedValue(strategy = GenerationType.AUTO)]
    abstract var id: KeyType?

    @get:[Access(AccessType.PROPERTY) Version]
    abstract var version: LocalDateTime?

    final override fun equals(other: Any?): Boolean =
        if (equalityProperties.isEmpty()) super.equals(other) else propertyEquals(this, other, equalityProperties)

    final override fun hashCode(): Int =
        if (equalityProperties.isEmpty()) super.hashCode() else propertyHashCode(this, equalityProperties)

    fun dataEquals(other: Any?): Boolean = propertyEquals(this, other, dataProperties)

    fun dataHashCode(): Int = propertyHashCode(this, dataProperties)

    private val equalityProperties: Collection<KProperty1<out DbEntity, Any?>>
        get() = equalityPropertiesByType.computeIfAbsent(this::class) { equalityProperties() }

    private val dataProperties: Collection<KProperty1<out DbEntity, Any?>>
        get() = dataPropertiesByType.computeIfAbsent(this::class) { dataProperties() }

    companion object {
        private val equalityPropertiesByType:
                MutableMap<KClass<out DbEntity>, Collection<KProperty1<out DbEntity, Any?>>> = mutableMapOf()
        private val dataPropertiesByType:
                MutableMap<KClass<out DbEntity>, Collection<KProperty1<out DbEntity, Any?>>> = mutableMapOf()
    }

    /**
     * Return a list of equality properties that are used for equals and hashCode.
     * The default is to return all declared public properties that are neither id nor version.
     */
    public open fun equalityProperties(): Collection<KProperty1<out DbEntity, Any?>> =
        dataProperties().filter { it.name != "id" && it.name != "version" }

    /**
     * Return all declared public properties.
     */
    private fun dataProperties(): Collection<KProperty1<out DbEntity, Any?>> =
        this::class.declaredMemberProperties.filter { it.visibility === KVisibility.PUBLIC }
}

internal inline fun <reified T> propertyEquals(
    first: T,
    second: Any?,
    properties: Collection<KProperty1<out T, Any?>>
): Boolean {
    if (first === second) return true
    if (second === null || second !is T) return false
    return properties.map {
        @Suppress("UNCHECKED_CAST")
        it as KProperty1<T, Any?>
    }.all { it.get(first) == it.get(second) }
}

internal fun <T> propertyHashCode(obj: T, properties: Collection<KProperty1<out T, Any?>>): Int =
    properties.map {
        @Suppress("UNCHECKED_CAST")
        it as KProperty1<T, Any?>
    }.map { it.get(obj) }.hashCode()
