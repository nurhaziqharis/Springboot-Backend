package com.brobarber.backend.model

import jakarta.persistence.*
import java.time.LocalTime

@Entity
@Table(name = "barbershops")
class Barbershop(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    var owner: User = User(),

    @Column(nullable = false)
    var name: String = "",

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(nullable = false)
    var address: String = "",

    @Column(name = "is_open", nullable = false)
    var isOpen: Boolean = false,

    @Column(name = "opening_time")
    var openingTime: LocalTime? = null,

    @Column(name = "closing_time")
    var closingTime: LocalTime? = null
)
