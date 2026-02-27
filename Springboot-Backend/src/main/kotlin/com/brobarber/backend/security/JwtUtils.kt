package com.brobarber.backend.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtils {

    @Value("\${app.jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${app.jwt.expiration-ms}")
    private var jwtExpirationMs: Long = 86400000

    @Value("\${app.jwt.cookie-name}")
    private lateinit var jwtCookieName: String

    private fun key(): SecretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun getJwtFromCookies(request: HttpServletRequest): String? {
        return request.cookies?.firstOrNull { it.name == jwtCookieName }?.value
    }

    fun generateJwtCookie(email: String): ResponseCookie {
        val token = generateToken(email)
        return ResponseCookie.from(jwtCookieName, token)
            .path("/")
            .maxAge(jwtExpirationMs / 1000)
            .httpOnly(true)
            .secure(false)
            .sameSite("Lax")
            .build()
    }

    fun generateCleanJwtCookie(): ResponseCookie {
        return ResponseCookie.from(jwtCookieName, "")
            .path("/")
            .maxAge(0)
            .httpOnly(true)
            .build()
    }

    fun generateToken(email: String): String {
        return Jwts.builder()
            .subject(email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key())
            .compact()
    }

    fun getEmailFromToken(token: String): String {
        return Jwts.parser()
            .verifyWith(key())
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}
