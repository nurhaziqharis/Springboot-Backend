package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import com.brobarber.backend.enums.SubscriptionStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "shop_subscriptions", indexes = [
    Index(name = "idx_subscription_shop", columnList = "shop_id"),
    Index(name = "idx_subscription_status", columnList = "status")
])
class ShopSubscription(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Barbershop,

    @Column(name = "started_at", nullable = false)
    var startedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "expires_at", nullable = false)
    var expiresAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: SubscriptionStatus = SubscriptionStatus.ACTIVE

) : BaseEntity()
