package com.brobarber.backend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class BarberRequest(
    @field:NotNull
    val userId: Long,

    @field:NotNull
    val shopId: Long,

    @field:NotBlank
    val name: String,

    val isActive: Boolean = true
)
