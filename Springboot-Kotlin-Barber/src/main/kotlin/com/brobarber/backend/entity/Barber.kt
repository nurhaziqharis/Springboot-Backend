package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "barbers", indexes = [
    Index(name = "idx_barber_user", columnList = "user_id"),
    Index(name = "idx_barber_shop", columnList = "shop_id"),
    Index(name = "idx_barber_active", columnList = "is_active")
])
class Barber(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Barbershop,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var bio: String? = null,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

) : BaseEntity() {

    @OneToMany(mappedBy = "barber", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var sessions: MutableList<BarberSession> = mutableListOf()

    @OneToMany(mappedBy = "barber", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var queues: MutableList<Queue> = mutableListOf()
}
