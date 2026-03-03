package com.brobarber.backend.repository

import com.brobarber.backend.entity.BarberSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface BarberSessionRepository : JpaRepository<BarberSession, Long> {

    @Query("SELECT bs FROM BarberSession bs WHERE bs.barber.id = :barberId AND bs.clockOut IS NULL")
    fun findActiveSessionByBarberId(barberId: Long): Optional<BarberSession>

    @Query("SELECT bs FROM BarberSession bs WHERE bs.barber.id = :barberId AND bs.clockIn >= :startDate AND bs.clockIn < :endDate")
    fun findAllByBarberIdAndDateRange(barberId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<BarberSession>

    @Query("SELECT bs FROM BarberSession bs WHERE bs.barber.shop.id = :shopId AND bs.clockIn >= :startDate AND bs.clockIn < :endDate")
    fun findAllByShopIdAndDateRange(shopId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<BarberSession>
}
