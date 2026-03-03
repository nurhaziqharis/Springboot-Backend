package com.brobarber.backend.repository

import com.brobarber.backend.entity.Queue
import com.brobarber.backend.enums.QueueStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import jakarta.persistence.LockModeType
import java.time.LocalDateTime
import java.util.*

@Repository
interface QueueRepository : JpaRepository<Queue, Long> {

    // Critical query with composite index (shop_id, status)
    @Query("SELECT q FROM Queue q WHERE q.shop.id = :shopId AND q.status = :status ORDER BY q.joinedAt ASC")
    fun findAllByShopIdAndStatus(shopId: Long, status: QueueStatus): List<Queue>

    // Optimistic locking for queue updates
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT q FROM Queue q WHERE q.id = :id")
    fun findByIdWithLock(id: Long): Optional<Queue>

    @Query("SELECT q FROM Queue q WHERE q.shop.id = :shopId AND q.status IN :statuses ORDER BY q.joinedAt ASC")
    fun findAllByShopIdAndStatusIn(shopId: Long, statuses: List<QueueStatus>): List<Queue>

    @Query("SELECT q FROM Queue q WHERE q.barber.id = :barberId AND q.status = :status ORDER BY q.joinedAt ASC")
    fun findAllByBarberIdAndStatus(barberId: Long, status: QueueStatus): List<Queue>

    @Query("SELECT q FROM Queue q WHERE q.client.id = :clientId ORDER BY q.joinedAt DESC")
    fun findAllByClientId(clientId: Long): List<Queue>

    @Query("SELECT q FROM Queue q WHERE q.guestUuid = :guestUuid ORDER BY q.joinedAt DESC")
    fun findAllByGuestUuid(guestUuid: String): List<Queue>

    fun findByQueueNumber(queueNumber: String): Optional<Queue>

    @Query("""
        SELECT q FROM Queue q
        WHERE q.shop.id = :shopId
        AND q.joinedAt >= :startDate
        AND q.joinedAt < :endDate
        ORDER BY q.joinedAt DESC
    """)
    fun findAllByShopIdAndDateRange(shopId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Queue>

    @Query("""
        SELECT COUNT(q) FROM Queue q
        WHERE q.shop.id = :shopId
        AND q.status IN ('WAITING', 'CALLING')
    """)
    fun countActiveQueuesByShopId(shopId: Long): Long

    @Query("""
        SELECT q FROM Queue q
        WHERE q.shop.id = :shopId
        AND q.barber.id = :barberId
        AND q.status = 'IN_PROGRESS'
    """)
    fun findCurrentServiceByBarber(shopId: Long, barberId: Long): Optional<Queue>
}
