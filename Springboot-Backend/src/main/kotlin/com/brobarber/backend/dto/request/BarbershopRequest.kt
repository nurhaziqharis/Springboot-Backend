package com.brobarber.backend.dto.request

import jakarta.validation.constraints.NotBlank

data class BarbershopRequest(
    @field:NotBlank
    val name: String,

    val description: String? = null,

    @field:NotBlank
    val address: String,

    val isOpen: Boolean = false,
    val openingTime: String? = null, // HH:mm
    val closingTime: String? = null  // HH:mm
)
