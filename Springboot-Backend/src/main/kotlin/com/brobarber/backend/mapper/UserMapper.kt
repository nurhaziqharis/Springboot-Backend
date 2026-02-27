package com.brobarber.backend.mapper

import com.brobarber.backend.dto.response.AuthResponse
import com.brobarber.backend.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toAuthResponse(user: User): AuthResponse = AuthResponse(
        id = user.id,
        email = user.email,
        name = user.name,
        role = user.role.name
    )
}
