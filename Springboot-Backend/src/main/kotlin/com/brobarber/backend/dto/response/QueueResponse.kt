package com.brobarber.backend.dto.response

import java.time.LocalDateTime

data class QueueResponse(
    val id: Long,
    val shopId: Long,
    val clientId: Long,
    val clientName: String,
    val barberId: Long?,
    val barberName: String?,
    val queueNumber: String,
    val status: String,
    val joinedAt: LocalDateTime,
    val startedAt: LocalDateTime?,
    val finishedAt: LocalDateTime?
)
