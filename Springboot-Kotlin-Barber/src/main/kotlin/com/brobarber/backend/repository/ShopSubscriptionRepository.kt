package com.brobarber.backend.repository

import com.brobarber.backend.entity.ShopSubscription
import com.brobarber.backend.enums.SubscriptionStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface ShopSubscriptionRepository : JpaRepository<ShopSubscription, Long> {

    fun findByShopIdAndStatus(shopId: Long, status: SubscriptionStatus): Optional<ShopSubscription>

    @Query("SELECT s FROM ShopSubscription s WHERE s.shop.id = :shopId ORDER BY s.expiresAt DESC")
    fun findLatestByShopId(shopId: Long): Optional<ShopSubscription>

    @Query("SELECT s FROM ShopSubscription s WHERE s.status = 'ACTIVE' AND s.expiresAt < :now")
    fun findExpiredSubscriptions(now: LocalDateTime = LocalDateTime.now()): List<ShopSubscription>
}
