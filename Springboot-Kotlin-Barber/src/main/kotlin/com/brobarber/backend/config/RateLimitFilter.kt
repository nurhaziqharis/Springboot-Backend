package com.brobarber.backend.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Duration

@Component
class RateLimitFilter(
    private val redisTemplate: RedisTemplate<String, String>,
    @Value("\${app.rate-limit.guest.requests-per-minute}")
    private val guestLimit: Int,
    @Value("\${app.rate-limit.registered.requests-per-minute}")
    private val registeredLimit: Int
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val clientId = getClientIdentifier(request)
        val isAuthenticated = SecurityContextHolder.getContext().authentication?.isAuthenticated == true
        val limit = if (isAuthenticated) registeredLimit else guestLimit

        val key = "rate_limit:$clientId"
        val requests = redisTemplate.opsForValue().increment(key) ?: 1L

        if (requests == 1L) {
            redisTemplate.expire(key, Duration.ofMinutes(1))
        }

        if (requests > limit) {
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.contentType = "application/json"
            response.writer.write("""
                {
                    "status": 429,
                    "error": "Too Many Requests",
                    "message": "Rate limit exceeded. Please try again later."
                }
            """.trimIndent())
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun getClientIdentifier(request: HttpServletRequest): String {
        val auth = SecurityContextHolder.getContext().authentication
        return if (auth?.isAuthenticated == true) {
            "user:${auth.name}"
        } else {
            "ip:${getClientIP(request)}"
        }
    }

    private fun getClientIP(request: HttpServletRequest): String {
        val xForwardedFor = request.getHeader("X-Forwarded-For")
        return if (xForwardedFor != null && xForwardedFor.isNotEmpty()) {
            xForwardedFor.split(",")[0].trim()
        } else {
            request.remoteAddr
        }
    }
}
