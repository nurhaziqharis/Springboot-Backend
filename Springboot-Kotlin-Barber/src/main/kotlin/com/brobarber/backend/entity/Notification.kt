package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notifications", indexes = [
    Index(name = "idx_notification_user", columnList = "user_id"),
    Index(name = "idx_notification_read", columnList = "is_read"),
    Index(name = "idx_notification_created", columnList = "created_at")
])
class Notification(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(nullable = false, length = 200)
    var title: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    var message: String,

    @Column(name = "is_read", nullable = false)
    var isRead: Boolean = false,

    @Column(name = "read_at")
    var readAt: LocalDateTime? = null

) : BaseEntity()
