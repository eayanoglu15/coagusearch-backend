package com.example.coagusearch.shared

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import arrow.core.some
import java.util.Optional

/**
 * Converts the given optional to arrow option
 * @return Optionals value as Option
 */
fun <T> Optional<T>.asOption(): Option<T> = Option.fromNullable(this.orElse(null))

/**
 * Gets the value of the option, throws if it is empty
 * @param err Inlined function for specifying the exception
 * @return The value holded in the option
 */
inline fun <T> Option<T>.getOrThrow(crossinline err: () -> Throwable): T = this.getOrElse { throw err() }

fun <T, U> Pair<Option<T>, Option<U>>.transposeBoth(): Option<Pair<T, U>> =
    first.flatMap { f ->
        second.map { s ->
            f to s
        }
    }

fun <T, U> Pair<Option<T>, U>.transposeFirst(): Option<Pair<T, U>> =
    first.map { f ->
        f to second
    }

fun <T, U> Pair<T, Option<U>>.transposeSecond(): Option<Pair<T, U>> =
    second.map { s ->
        first to s
    }

/**
 * Returns Some(list) if the list is not empty, None if the list is empty
 */
fun <T, C : Collection<T>> C?.asNonEmpty(): Option<C> =
    if (this.isNullOrEmpty()) {
        None
    } else {
        this.some()
    }

inline fun <T> Option<T>.flatMapLeft(fn: () -> Option<T>): Option<T> =
    when (this) {
        is Some -> this
        else -> fn()
    }
