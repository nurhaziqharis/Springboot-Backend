package com.brobarber.backend.repository

import com.brobarber.backend.entity.User
import com.brobarber.backend.enums.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>

    fun findByPhoneNumber(phoneNumber: String): Optional<User>

    fun existsByEmail(email: String): Boolean

    fun existsByPhoneNumber(phoneNumber: String): Boolean

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND u.email = :email")
    fun findActiveByEmail(email: String): Optional<User>

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND u.role = :role")
    fun findAllActiveByRole(role: Role): List<User>

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL")
    fun findAllActive(): List<User>
}
