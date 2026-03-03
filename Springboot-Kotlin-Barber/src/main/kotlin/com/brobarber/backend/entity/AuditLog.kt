package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "audit_logs", indexes = [
    Index(name = "idx_audit_admin", columnList = "admin_id"),
    Index(name = "idx_audit_action", columnList = "action"),
    Index(name = "idx_audit_performed_at", columnList = "performed_at")
])
class AuditLog(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    var admin: User,

    @Column(nullable = false, length = 100)
    var action: String,

    @Column(columnDefinition = "TEXT")
    var details: String? = null,

    @Column(name = "performed_at", nullable = false)
    var performedAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity()
