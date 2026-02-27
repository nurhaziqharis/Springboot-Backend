package com.brobarber.backend.repository

import com.brobarber.backend.model.Barber
import org.springframework.data.jpa.repository.JpaRepository

interface BarberRepository : JpaRepository<Barber, Long> {
    fun findByShopId(shopId: Long): List<Barber>
    fun findByUserId(userId: Long): Barber?
    fun findByShopIdAndIsActiveTrue(shopId: Long): List<Barber>
}
