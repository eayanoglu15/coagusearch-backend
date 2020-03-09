package com.example.coagusearch.security

import io.jsonwebtoken.security.Keys
import javax.crypto.SecretKey

object SecurityConstants {
    @Suppress("MaxLineLength")
    private const val SECRET_KEY =
        "9kJFHE3mT-iuli65GFQwqhbvvZ8Pr1NcVBSGhNunaYC1FnJHOArxIwTizVPSzXbV5wGABZpfVCN5fsJjcuBarQ"

    val SECRET: SecretKey
        get() = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())

    // TODO : Make it 600000 again
    const val EXPIRATION_TIME: Long = 600000 // 10 minutes

    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
}
