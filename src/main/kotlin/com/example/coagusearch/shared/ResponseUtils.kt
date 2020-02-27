package com.example.coagusearch.shared

import org.springframework.http.ResponseEntity

/**
 * Converts the given variable to a ResponseEntity with Status 200
 * @return Variable as a response entity
 */
fun <T> T.asOkResponse(): ResponseEntity<T> =
        ResponseEntity.ok(this)
