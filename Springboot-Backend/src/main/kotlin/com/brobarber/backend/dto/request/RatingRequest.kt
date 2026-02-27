package com.brobarber.backend.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class RatingRequest(
    @field:NotNull
    val queueId: Long,

    @field:NotNull @field:Min(1) @field:Max(10)
    val score: Int,

    val comment: String? = null
)
