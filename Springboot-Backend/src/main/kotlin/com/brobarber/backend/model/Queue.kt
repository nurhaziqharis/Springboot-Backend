package com.brobarber.backend.model

import com.brobarber.backend.enums.QueueStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "queues")
class Queue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Barbershop = Barbershop(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    var client: User = User(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id")
    var barber: Barber? = null,

    @Column(name = "queue_number", nullable = false)
    var queueNumber: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: QueueStatus = QueueStatus.WAITING,

    @Column(name = "joined_at", nullable = false)
    var joinedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "started_at")
    var startedAt: LocalDateTime? = null,

    @Column(name = "finished_at")
    var finishedAt: LocalDateTime? = null
)
