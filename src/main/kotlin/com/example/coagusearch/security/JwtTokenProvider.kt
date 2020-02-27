package com.example.coagusearch.security


import com.example.coagusearch.modules.auth.response.JwtAuthenticationResponse
import com.example.coagusearch.modules.auth.response.JwtRefreshResponse
import com.example.coagusearch.shared.RestException
import com.example.coagusearch.modules.users.model.User
import io.jsonwebtoken.CompressionCodecs
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenProvider @Autowired constructor(
    //@Value("\${app.jwt.secret}")

) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
        private val jwtSecret: String? = "A23BE6FF35EDFD28BEF4656C55FCF4C905CBBD9EA1F33F44848839FC91ABE383"
    }

    val secret by lazy {
        generateSecret()
    }

    private fun generateSecret(): SecretKey {
        if (jwtSecret.isNullOrBlank()) {
            return SecurityConstants.SECRET
        }
        return Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

    fun generateToken(user: User): JwtAuthenticationResponse {
        val now = Date()
        val expiryDate = Date(now.time + SecurityConstants.EXPIRATION_TIME)

        val accessToken = Jwts.builder()
            .setSubject(user.id.toString())
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(secret, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken = Jwts.builder()
            .setSubject(user.id.toString())
            .setIssuedAt(Date())
            .setExpiration(null)
            .signWith(secret, SignatureAlgorithm.HS512)
            .compressWith(CompressionCodecs.GZIP)
            .compact()

        return JwtAuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun generateToken(authentication: Authentication): JwtAuthenticationResponse {
        val userPrincipal = authentication.principal as UserPrincipal
        return generateToken(userPrincipal.user)
    }

    fun regenerateToken(userId: Long): JwtRefreshResponse {
        val now = Date()
        val expiryDate = Date(now.time + SecurityConstants.EXPIRATION_TIME)

        val accessToken = Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(secret, SignatureAlgorithm.HS512)
            .compact()

        return JwtRefreshResponse(
            accessToken = accessToken
        )
    }

    fun getUserIdFromJWT(token: String?): Long {
        val claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body

        return claims.subject.toLong()
    }

    fun validateToken(authToken: String?): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(authToken)
            true
        } catch (ex: Exception) {
            logger.warn(
                when (ex) {
                    is MalformedJwtException -> "Invalid JWT token"
                    is ExpiredJwtException -> "Expired JWT token"
                    is UnsupportedJwtException -> "Unsupported JWT token"
                    is IllegalArgumentException -> "JWT claims string is empty"
                    else -> throw RestException(
                        message = "Exception.unexpected",
                        statusCode = HttpStatus.UNAUTHORIZED
                    ).apply {
                        hiddenMessage = ex.toString()
                    }
                }
            )
            false
        }
    }
}
