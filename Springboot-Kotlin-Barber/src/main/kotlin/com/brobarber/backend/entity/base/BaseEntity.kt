package com.brobarber.backend.entity.base

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    open var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(name = "updated_at")
    open var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "deleted_at")
    open var deletedAt: LocalDateTime? = null

    @Version
    @Column(name = "version")
    open var version: Long = 0

    val isDeleted: Boolean
        get() = deletedAt != null

    fun softDelete() {
        deletedAt = LocalDateTime.now()
    }

    fun restore() {
        deletedAt = null
    }
}
