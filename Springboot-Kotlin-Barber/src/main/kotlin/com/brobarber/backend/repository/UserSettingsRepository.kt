package com.brobarber.backend.repository

import com.brobarber.backend.entity.UserSettings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserSettingsRepository : JpaRepository<UserSettings, Long> {

    fun findByUserId(userId: Long): Optional<UserSettings>
}
