package com.brobarber.backend.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils(
    @Value("\${app.jwt.secret}")
    private val jwtSecret: String,

    @Value("\${app.jwt.access-token-expiration-ms}")
    private val accessTokenExpirationMs: Long,

    @Value("\${app.jwt.refresh-token-expiration-ms}")
    private val refreshTokenExpirationMs: Long
) {

    private val logger = LoggerFactory.getLogger(JwtUtils::class.java)
    private val key: SecretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun generateAccessToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl
        return generateToken(userPrincipal.username, accessTokenExpirationMs, "ACCESS")
    }

    fun generateRefreshToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl
        return generateToken(userPrincipal.username, refreshTokenExpirationMs, "REFRESH")
    }

    fun generateAccessTokenFromEmail(email: String): String {
        return generateToken(email, accessTokenExpirationMs, "ACCESS")
    }

    fun generateRefreshTokenFromEmail(email: String): String {
        return generateToken(email, refreshTokenExpirationMs, "REFRESH")
    }

    private fun generateToken(email: String, expirationMs: Long, type: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationMs)

        return Jwts.builder()
            .subject(email)
            .claim("type", type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun getEmailFromToken(token: String): String {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.subject
    }

    fun getExpirationFromToken(token: String): Date {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.expiration
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(authToken)
            return true
        } catch (e: SecurityException) {
            logger.error("Invalid JWT signature: ${e.message}")
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: ${e.message}")
        }

        return false
    }
}
