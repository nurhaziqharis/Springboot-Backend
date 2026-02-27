package com.brobarber.backend.dto.response

data class RatingResponse(
    val id: Long,
    val queueId: Long,
    val score: Int,
    val comment: String?
)
