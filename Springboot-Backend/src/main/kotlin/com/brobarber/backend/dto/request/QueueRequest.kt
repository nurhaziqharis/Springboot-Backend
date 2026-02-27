package com.brobarber.backend.dto.request

import jakarta.validation.constraints.NotNull

data class QueueRequest(
    @field:NotNull
    val shopId: Long,

    val barberId: Long? = null
)
