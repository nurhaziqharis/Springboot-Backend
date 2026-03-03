package com.brobarber.backend.security

import com.brobarber.backend.entity.User
import com.brobarber.backend.enums.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val id: Long,
    private val email: String,
    val name: String,
    val role: Role,
    @JsonIgnore
    private val password: String
) : UserDetails {

    companion object {
        fun build(user: User): UserDetailsImpl {
            return UserDetailsImpl(
                id = user.id,
                email = user.email,
                name = user.name,
                role = user.role,
                password = user.passwordHash
            )
        }
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_${role.name}"))
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as UserDetailsImpl
        return id == user.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
