package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_settings")
class UserSettings(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    var user: User,

    @Column(name = "dark_mode_preferred", nullable = false)
    var darkModePreferred: Boolean = true, // Always true for v1.0

    @Column(nullable = false, length = 10)
    var language: String = "en", // English only for v1.0

    @Column(name = "notifications_muted", nullable = false)
    var notificationsMuted: Boolean = false

) : BaseEntity()
