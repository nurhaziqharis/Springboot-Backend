package com.brobarber.backend.repository

import com.brobarber.backend.entity.AuditLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface AuditLogRepository : JpaRepository<AuditLog, Long> {

    @Query("SELECT a FROM AuditLog a ORDER BY a.performedAt DESC")
    fun findAllOrderByPerformedAtDesc(): List<AuditLog>

    @Query("SELECT a FROM AuditLog a WHERE a.admin.id = :adminId ORDER BY a.performedAt DESC")
    fun findAllByAdminId(adminId: Long): List<AuditLog>

    @Query("SELECT a FROM AuditLog a WHERE a.action = :action ORDER BY a.performedAt DESC")
    fun findAllByAction(action: String): List<AuditLog>

    @Query("SELECT a FROM AuditLog a WHERE a.performedAt >= :startDate AND a.performedAt < :endDate ORDER BY a.performedAt DESC")
    fun findAllByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<AuditLog>
}
