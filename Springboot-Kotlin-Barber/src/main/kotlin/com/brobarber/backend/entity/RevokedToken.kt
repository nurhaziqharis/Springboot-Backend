package com.brobarber.backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "revoked_tokens", indexes = [
    Index(name = "idx_revoked_token_expires", columnList = "expires_at")
])
class RevokedToken(

    @Id
    @Column(length = 500)
    var token: String,

    @Column(name = "expires_at", nullable = false)
    var expiresAt: LocalDateTime

) {
    @Column(name = "revoked_at", nullable = false, updatable = false)
    var revokedAt: LocalDateTime = LocalDateTime.now()
}
