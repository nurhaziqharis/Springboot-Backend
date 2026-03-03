package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.time.LocalTime

@Entity
@Table(name = "barbershops", indexes = [
    Index(name = "idx_barbershop_owner", columnList = "owner_id"),
    Index(name = "idx_barbershop_is_open", columnList = "is_open")
])
class Barbershop(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    var owner: User,

    @Column(nullable = false, length = 200)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(nullable = false, length = 500)
    var address: String,

    @Column(name = "is_open", nullable = false)
    var isOpen: Boolean = false,

    @Column(name = "opening_time")
    var openingTime: LocalTime? = null,

    @Column(name = "closing_time")
    var closingTime: LocalTime? = null,

    @Type(JsonBinaryType::class)
    @Column(name = "split_shifts_config", columnDefinition = "jsonb")
    var splitShiftsConfig: Map<String, Any>? = null,

    @Column(name = "average_rating_cached", precision = 3, scale = 2)
    var averageRatingCached: BigDecimal = BigDecimal.ZERO

) : BaseEntity() {

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var barbers: MutableList<Barber> = mutableListOf()

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var serviceCategories: MutableList<ServiceCategory> = mutableListOf()

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var galleries: MutableList<ShopGallery> = mutableListOf()

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var subscriptions: MutableList<ShopSubscription> = mutableListOf()

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var queues: MutableList<Queue> = mutableListOf()
}
