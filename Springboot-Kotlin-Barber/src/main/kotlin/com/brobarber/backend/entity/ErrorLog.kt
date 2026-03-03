package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "error_logs", indexes = [
    Index(name = "idx_error_level", columnList = "level"),
    Index(name = "idx_error_logged_at", columnList = "logged_at")
])
class ErrorLog(

    @Column(nullable = false, length = 20)
    var level: String, // ERROR, WARN, INFO

    @Column(columnDefinition = "TEXT", nullable = false)
    var message: String,

    @Column(name = "stack_trace", columnDefinition = "TEXT")
    var stackTrace: String? = null,

    @Column(name = "logged_at", nullable = false)
    var loggedAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity()
