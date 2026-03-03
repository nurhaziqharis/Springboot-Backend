package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import com.brobarber.backend.enums.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
@Table(name = "users", indexes = [
    Index(name = "idx_user_email", columnList = "email"),
    Index(name = "idx_user_phone", columnList = "phone_number"),
    Index(name = "idx_user_role", columnList = "role")
])
class User(

    @Column(unique = true, nullable = false, length = 255)
    @Email
    var email: String,

    @Column(name = "phone_number", unique = true, length = 20)
    var phoneNumber: String? = null,

    @Column(name = "profile_picture_url", length = 500)
    var profilePictureUrl: String? = null,

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String,

    @Column(nullable = false, length = 100)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var role: Role = Role.CLIENT,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_by_id")
    var referredBy: User? = null

) : BaseEntity() {

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var settings: UserSettings? = null

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var ownedBarbershops: MutableList<Barbershop> = mutableListOf()

    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var queues: MutableList<Queue> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var notifications: MutableList<Notification> = mutableListOf()
}
