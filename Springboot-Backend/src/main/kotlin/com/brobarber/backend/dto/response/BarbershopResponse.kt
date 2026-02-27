package com.brobarber.backend.dto.response

data class BarbershopResponse(
    val id: Long,
    val ownerId: Long,
    val ownerName: String,
    val name: String,
    val description: String?,
    val address: String,
    val isOpen: Boolean,
    val openingTime: String?,
    val closingTime: String?,
    val averageRating: Double?
)
