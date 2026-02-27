package com.brobarber.backend.mapper

import com.brobarber.backend.dto.response.RatingResponse
import com.brobarber.backend.model.Rating
import org.springframework.stereotype.Component

@Component
class RatingMapper {

    fun toResponse(entity: Rating): RatingResponse = RatingResponse(
        id = entity.id,
        queueId = entity.queue.id,
        score = entity.score,
        comment = entity.comment
    )
}
