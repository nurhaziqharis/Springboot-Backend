package com.brobarber.backend.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)

data class RegisterRequest(
    @field:NotBlank @field:Email
    val email: String,

    @field:NotBlank @field:Size(min = 6)
    val password: String,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val role: String // CLIENT, BARBER, SHOP_OWNER
)
