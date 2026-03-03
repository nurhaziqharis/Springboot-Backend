package com.brobarber.backend.repository

import com.brobarber.backend.entity.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface RatingRepository : JpaRepository<Rating, Long> {

    fun findByQueueId(queueId: Long): Optional<Rating>

    @Query("""
        SELECT r FROM Rating r
        WHERE r.queue.shop.id = :shopId
        ORDER BY r.createdAt DESC
    """)
    fun findAllByShopId(shopId: Long): List<Rating>

    @Query("""
        SELECT AVG(r.score) FROM Rating r
        WHERE r.queue.shop.id = :shopId
        AND r.createdAt >= :startDate
    """)
    fun calculateAverageRatingByShopId(shopId: Long, startDate: LocalDateTime): Double?

    @Query("""
        SELECT r FROM Rating r
        WHERE r.queue.shop.id = :shopId
        AND r.createdAt >= :thirtyDaysAgo
        ORDER BY r.score DESC
    """)
    fun findTopRatedInLast30Days(shopId: Long, thirtyDaysAgo: LocalDateTime): List<Rating>

    @Query("""
        SELECT r FROM Rating r
        WHERE r.queue.barber.id = :barberId
        ORDER BY r.createdAt DESC
    """)
    fun findAllByBarberId(barberId: Long): List<Rating>
}
