package com.brobarber.backend.dto.response

data class BarberResponse(
    val id: Long,
    val userId: Long,
    val shopId: Long,
    val name: String,
    val isActive: Boolean
)
