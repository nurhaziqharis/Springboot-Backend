package com.brobarber.backend.repository

import com.brobarber.backend.entity.Barbershop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BarbershopRepository : JpaRepository<Barbershop, Long> {

    @Query("SELECT b FROM Barbershop b WHERE b.deletedAt IS NULL")
    fun findAllActive(): List<Barbershop>

    @Query("SELECT b FROM Barbershop b WHERE b.deletedAt IS NULL AND b.id = :id")
    fun findActiveById(id: Long): Barbershop?

    @Query("SELECT b FROM Barbershop b WHERE b.deletedAt IS NULL AND b.owner.id = :ownerId")
    fun findAllActiveByOwnerId(ownerId: Long): List<Barbershop>

    @Query("SELECT b FROM Barbershop b WHERE b.deletedAt IS NULL AND b.isOpen = true")
    fun findAllActiveAndOpen(): List<Barbershop>

    @Query("""
        SELECT b FROM Barbershop b
        WHERE b.deletedAt IS NULL
        AND (LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(b.address) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    fun searchActiveByKeyword(keyword: String): List<Barbershop>

    @Query("""
        SELECT b FROM Barbershop b
        WHERE b.deletedAt IS NULL
        ORDER BY b.averageRatingCached DESC
    """)
    fun findAllActiveOrderByRating(): List<Barbershop>
}
