package com.brobarber.backend.repository

import com.brobarber.backend.enums.QueueStatus
import com.brobarber.backend.model.Queue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QueueRepository : JpaRepository<Queue, Long> {
    fun findByShopIdAndStatus(shopId: Long, status: QueueStatus): List<Queue>
    fun findByClientIdAndStatus(clientId: Long, status: QueueStatus): List<Queue>
    fun findByBarberIdAndStatus(barberId: Long, status: QueueStatus): List<Queue>
    fun findByShopIdOrderByJoinedAtAsc(shopId: Long): List<Queue>

    @Query("SELECT COUNT(q) FROM Queue q WHERE q.shop.id = :shopId AND q.queueNumber LIKE :datePrefix%")
    fun countByShopIdAndQueueNumberStartingWith(shopId: Long, datePrefix: String): Long

    @Query("SELECT COUNT(q) FROM Queue q WHERE q.barber.id = :barberId AND q.status = 'FINISHED' AND CAST(q.finishedAt AS date) = CURRENT_DATE")
    fun countFinishedTodayByBarberId(barberId: Long): Long
}
