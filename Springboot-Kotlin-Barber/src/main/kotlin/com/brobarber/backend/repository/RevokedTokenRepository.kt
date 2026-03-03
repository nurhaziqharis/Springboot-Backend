package com.brobarber.backend.repository

import com.brobarber.backend.entity.RevokedToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface RevokedTokenRepository : JpaRepository<RevokedToken, String> {

    fun existsByToken(token: String): Boolean

    @Modifying
    @Query("DELETE FROM RevokedToken rt WHERE rt.expiresAt < :now")
    fun deleteExpiredTokens(now: LocalDateTime = LocalDateTime.now()): Int
}
