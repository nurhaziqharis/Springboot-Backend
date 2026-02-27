package com.brobarber.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "ratings")
class Rating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id", nullable = false, unique = true)
    var queue: Queue = Queue(),

    @Column(nullable = false)
    var score: Int = 0,

    @Column(columnDefinition = "TEXT")
    var comment: String? = null
)
