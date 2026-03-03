package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import com.brobarber.backend.enums.QueueStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "queues", indexes = [
    Index(name = "idx_queue_shop_status", columnList = "shop_id, status"), // Composite index for performance
    Index(name = "idx_queue_client", columnList = "client_id"),
    Index(name = "idx_queue_guest_uuid", columnList = "guest_uuid"),
    Index(name = "idx_queue_barber", columnList = "barber_id"),
    Index(name = "idx_queue_number", columnList = "queue_number"),
    Index(name = "idx_queue_joined_at", columnList = "joined_at")
])
class Queue(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Barbershop,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    var client: User? = null,

    @Column(name = "guest_uuid", length = 36)
    var guestUuid: String? = null, // Persistent UUID for guest users

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id")
    var barber: Barber? = null,

    @Column(name = "queue_number", nullable = false, unique = true, length = 20)
    var queueNumber: String, // Format: YYYYMMDD0001

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: QueueStatus = QueueStatus.WAITING,

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    var cancellationReason: String? = null,

    @Column(name = "joined_at", nullable = false)
    var joinedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "started_at")
    var startedAt: LocalDateTime? = null,

    @Column(name = "finished_at")
    var finishedAt: LocalDateTime? = null,

    @Column(name = "original_ewt_minutes")
    var originalEwtMinutes: Int? = null, // Estimated Wait Time

    @Column(name = "actual_wait_time_minutes")
    var actualWaitTimeMinutes: Int? = null

) : BaseEntity() {

    @OneToMany(mappedBy = "queue", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var queueServices: MutableList<QueueService> = mutableListOf()

    @OneToOne(mappedBy = "queue", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var rating: Rating? = null
}
