package com.brobarber.backend.service

import com.brobarber.backend.dto.request.RatingRequest
import com.brobarber.backend.dto.response.RatingResponse

interface RatingService {
    fun create(request: RatingRequest, clientEmail: String): RatingResponse
    fun getByQueue(queueId: Long): RatingResponse?
    fun getByShop(shopId: Long): List<RatingResponse>
}
