package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "barber_sessions", indexes = [
    Index(name = "idx_session_barber", columnList = "barber_id"),
    Index(name = "idx_session_clock_in", columnList = "clock_in")
])
class BarberSession(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id", nullable = false)
    var barber: Barber,

    @Column(name = "clock_in", nullable = false)
    var clockIn: LocalDateTime = LocalDateTime.now(),

    @Column(name = "clock_out")
    var clockOut: LocalDateTime? = null

) : BaseEntity()
