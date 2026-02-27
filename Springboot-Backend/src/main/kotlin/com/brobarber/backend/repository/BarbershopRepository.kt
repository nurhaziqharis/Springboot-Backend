package com.brobarber.backend.repository

import com.brobarber.backend.model.Barbershop
import org.springframework.data.jpa.repository.JpaRepository

interface BarbershopRepository : JpaRepository<Barbershop, Long> {
    fun findByOwnerId(ownerId: Long): List<Barbershop>
    fun findByIsOpenTrue(): List<Barbershop>
}
