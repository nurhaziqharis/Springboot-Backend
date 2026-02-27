package com.brobarber.backend.service.impl

import com.brobarber.backend.dto.request.RatingRequest
import com.brobarber.backend.dto.response.RatingResponse
import com.brobarber.backend.enums.QueueStatus
import com.brobarber.backend.mapper.RatingMapper
import com.brobarber.backend.model.Rating
import com.brobarber.backend.repository.QueueRepository
import com.brobarber.backend.repository.RatingRepository
import com.brobarber.backend.repository.UserRepository
import com.brobarber.backend.service.RatingService
import org.springframework.stereotype.Service

@Service
class RatingServiceImpl(
    private val ratingRepository: RatingRepository,
    private val queueRepository: QueueRepository,
    private val userRepository: UserRepository,
    private val ratingMapper: RatingMapper
) : RatingService {

    override fun create(request: RatingRequest, clientEmail: String): RatingResponse {
        val queue = queueRepository.findById(request.queueId)
            .orElseThrow { NoSuchElementException("Queue not found") }
        val client = userRepository.findByEmail(clientEmail)
            .orElseThrow { IllegalArgumentException("Client not found") }

        if (queue.client.id != client.id) {
            throw IllegalAccessException("You can only rate your own queue session")
        }

        if (queue.status != QueueStatus.FINISHED) {
            throw IllegalStateException("Can only rate finished sessions")
        }

        if (ratingRepository.findByQueueId(request.queueId) != null) {
            throw IllegalStateException("Rating already exists for this queue")
        }

        val rating = Rating(
            queue = queue,
            score = request.score,
            comment = request.comment
        )

        val saved = ratingRepository.save(rating)
        return ratingMapper.toResponse(saved)
    }

    override fun getByQueue(queueId: Long): RatingResponse? =
        ratingRepository.findByQueueId(queueId)?.let { ratingMapper.toResponse(it) }

    override fun getByShop(shopId: Long): List<RatingResponse> =
        ratingRepository.findAllByShopId(shopId).map { ratingMapper.toResponse(it) }
}
