package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "queue_services", indexes = [
    Index(name = "idx_queue_service_queue", columnList = "queue_id"),
    Index(name = "idx_queue_service_service", columnList = "service_id")
])
class QueueService(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id", nullable = false)
    var queue: Queue,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    var service: Service

) : BaseEntity()
