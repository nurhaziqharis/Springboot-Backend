package com.brobarber.backend.dto.response

data class AuthResponse(
    val id: Long,
    val email: String,
    val name: String,
    val role: String
)

data class MessageResponse(
    val message: String
)
