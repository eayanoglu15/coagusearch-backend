package com.example.coagusearch.shared

import java.net.MalformedURLException
import java.net.URL
import javax.servlet.http.HttpServletRequest
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Returns non-null value, throws if value is null
 * @param err Inlined function for specifying the error
 * @return Non null value of the variable
 */
@UseExperimental(ExperimentalContracts::class)
inline fun <T> T?.requireNotNull(err: () -> Throwable): T {
    contract {
        returns() implies (this@requireNotNull != null)
    }
    if (this != null)
        return this
    throw err()
}

/**
 * Returns non-null value, throws IllegalArgumentException if value is null
 * @return Non null value of the variable
 * @throws IllegalArgumentException if given variable is null
 */
fun <T> T?.requireNotNull(): T =
    this.requireNotNull { IllegalArgumentException() }

@UseExperimental(ExperimentalContracts::class)
inline fun <T, R> Iterable<T>.mapToSet(transform: (T) -> R): Set<R> {
    contract {
        callsInPlace(transform)
        returnsNotNull()
    }
    return mapTo(HashSet(), transform)
}

fun <T> List<T?>.sequence(): List<T>? {
    @Suppress("UNCHECKED_CAST")
    return if (this.any { it == null }) null else this.map { it!! }
}

fun <T, U> Pair<T?, U?>.transpose(): Pair<T, U>? =
    if (first == null || second == null) {
        null
    } else {
        first!! to second!!
    }

@Throws(MalformedURLException::class)
fun HttpServletRequest.getUrlBase(): String {
    val requestUrl = URL(requestURL.toString())
    val portString = if (requestUrl.port == -1) "" else ":" + requestUrl.port
    return requestUrl.protocol +
            "://" + requestUrl.host + portString + "/"
}

inline fun <R, A> ifNotNull(a: A?, block: (A) -> R): R? =
    if (a != null) {
        block(a)
    } else null

inline fun <R, A, B> ifNotNull(a: A?, b: B?, block: (A, B) -> R): R? =
    if (a != null && b != null) {
        block(a, b)
    } else null

inline fun <R, A, B, C> ifNotNull(a: A?, b: B?, c: C?, block: (A, B, C) -> R): R? =
    if (a != null && b != null && c != null) {
        block(a, b, c)
    } else null

inline fun <R, A, B, C, D> ifNotNull(a: A?, b: B?, c: C?, d: D?, block: (A, B, C, D) -> R): R? =
    if (a != null && b != null && c != null && d != null) {
        block(a, b, c, d)
    } else null

fun <T> Collection<T>.randomOrNull(): T? =
    if (size > 0) {
        this.random()
    } else {
        null
    }

inline fun <K, V, VT> Map<K, V>.mapNotNullValues(transform: (V) -> VT?): Map<K, VT> {
    return mapNotNull { (key, value) ->
        transform(value)?.let { key to it }
    }.toMap()
}

fun <T> T.asList(): List<T> = listOf(this)
