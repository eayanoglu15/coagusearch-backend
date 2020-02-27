package com.example.coagusearch.security

import arrow.core.Either
import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.shared.RestException
import org.springframework.http.HttpStatus
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class RandomTokenGenerator {
    companion object {
        val random: ThreadLocal<SecureRandom> = ThreadLocal.withInitial { SecureRandom() }
        val encoder: ThreadLocal<Base64.Encoder> = ThreadLocal.withInitial { Base64.getUrlEncoder().withoutPadding() }
        val secretDecoder: ThreadLocal<Base64.Decoder> = ThreadLocal.withInitial { Base64.getDecoder() }
        const val algorithm = "HmacSHA512"
    }

    fun generateToken(bytes: Int = 20): String {
        val buffer = ByteArray(bytes)
        random.get().nextBytes(buffer)
        return encoder.get().encodeToString(buffer)
    }

    fun generateTokenWithUserId(userId: KeyType, separator: String = ":"): String {
        return generateToken() + separator + "$userId"
    }

    fun generateSignedTokenWithUserId(secret: String, userId: KeyType, separator: String = ":"): String {
        val token = generateToken() + separator + "$userId"
        val secretBytes = secretDecoder.get().decode(secret)
        val key = SecretKeySpec(secretBytes, algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(key)
        val signature = mac.doFinal(token.toByteArray(Charsets.UTF_8))
        return token + separator + encoder.get().encodeToString(signature)
    }

    fun validateTokenAndRetriveUserId(
        token: String,
        secret: String,
        separator: String = ":"
    ): Either<RestException, KeyType> {
        try {
            val (rndBytes, uid, signatureGivenB64) = token.split(separator)
            val secretBytes = secretDecoder.get().decode(secret)
            val key = SecretKeySpec(secretBytes, algorithm)
            val mac = Mac.getInstance(algorithm)
            mac.init(key)
            val signatureActual = mac.doFinal("$rndBytes$separator$uid".toByteArray(Charsets.UTF_8))
            val signatureActualB64 = encoder.get().encodeToString(signatureActual)
            if (signatureGivenB64 != signatureActualB64) {
                throw RuntimeException("Invalid signature!")
            }
            return Either.right(uid.toLong())
        } catch (ex: Exception) {
            return Either.left(RestException("Auth.invalidVerificationCode", HttpStatus.UNAUTHORIZED)
                .apply { hiddenMessage = ex.message })
        }
    }
}
