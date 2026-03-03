package com.brobarber.backend.repository

import com.brobarber.backend.entity.ErrorLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ErrorLogRepository : JpaRepository<ErrorLog, Long> {

    @Query("SELECT e FROM ErrorLog e ORDER BY e.loggedAt DESC")
    fun findAllOrderByLoggedAtDesc(): List<ErrorLog>

    @Query("SELECT e FROM ErrorLog e WHERE e.level = :level ORDER BY e.loggedAt DESC")
    fun findAllByLevel(level: String): List<ErrorLog>

    @Query("SELECT e FROM ErrorLog e WHERE e.loggedAt >= :startDate AND e.loggedAt < :endDate ORDER BY e.loggedAt DESC")
    fun findAllByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<ErrorLog>
}
