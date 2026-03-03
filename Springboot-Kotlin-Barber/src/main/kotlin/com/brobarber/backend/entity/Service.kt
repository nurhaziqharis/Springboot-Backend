package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "services", indexes = [
    Index(name = "idx_service_category", columnList = "category_id"),
    Index(name = "idx_service_shop", columnList = "shop_id")
])
class Service(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var category: ServiceCategory,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Barbershop,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(name = "estimated_duration_minutes", nullable = false)
    var estimatedDurationMinutes: Int,

    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    var basePrice: BigDecimal,

    @Column(name = "dynamic_pricing_enabled", nullable = false)
    var dynamicPricingEnabled: Boolean = false

) : BaseEntity() {

    @OneToMany(mappedBy = "service", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var queueServices: MutableList<QueueService> = mutableListOf()
}
