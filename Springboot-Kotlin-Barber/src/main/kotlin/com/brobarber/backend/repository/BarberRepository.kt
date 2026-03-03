package com.brobarber.backend.repository

import com.brobarber.backend.entity.Barber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BarberRepository : JpaRepository<Barber, Long> {

    @Query("SELECT b FROM Barber b WHERE b.deletedAt IS NULL AND b.shop.id = :shopId")
    fun findAllActiveByShopId(shopId: Long): List<Barber>

    @Query("SELECT b FROM Barber b WHERE b.deletedAt IS NULL AND b.shop.id = :shopId AND b.isActive = true")
    fun findAllActiveAndAvailableByShopId(shopId: Long): List<Barber>

    @Query("SELECT b FROM Barber b WHERE b.deletedAt IS NULL AND b.user.id = :userId")
    fun findActiveByUserId(userId: Long): Barber?

    @Query("SELECT b FROM Barber b WHERE b.deletedAt IS NULL AND b.id = :id")
    fun findActiveById(id: Long): Barber?
}
