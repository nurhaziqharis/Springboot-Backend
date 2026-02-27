package com.brobarber.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "barbers")
class Barber(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    var user: User = User(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Barbershop = Barbershop(),

    @Column(nullable = false)
    var name: String = "",

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true
)
