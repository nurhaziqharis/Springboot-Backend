package com.brobarber.backend.repository

import com.brobarber.backend.model.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RatingRepository : JpaRepository<Rating, Long> {
    fun findByQueueId(queueId: Long): Rating?

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.queue.shop.id = :shopId")
    fun averageScoreByShopId(shopId: Long): Double?

    @Query("SELECT r FROM Rating r WHERE r.queue.shop.id = :shopId")
    fun findAllByShopId(shopId: Long): List<Rating>
}
