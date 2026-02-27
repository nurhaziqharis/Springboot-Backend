package com.brobarber.backend.service.impl

import com.brobarber.backend.dto.request.LoginRequest
import com.brobarber.backend.dto.request.RegisterRequest
import com.brobarber.backend.dto.response.AuthResponse
import com.brobarber.backend.enums.Role
import com.brobarber.backend.mapper.UserMapper
import com.brobarber.backend.model.User
import com.brobarber.backend.repository.UserRepository
import com.brobarber.backend.security.JwtUtils
import com.brobarber.backend.service.AuthService
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val userMapper: UserMapper
) : AuthService {

    override fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already in use")
        }

        val user = User(
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            name = request.name,
            role = Role.valueOf(request.role)
        )

        val saved = userRepository.save(user)
        return userMapper.toAuthResponse(saved)
    }

    override fun login(request: LoginRequest): Pair<AuthResponse, ResponseCookie> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        val user = userRepository.findByEmail(request.email)
            .orElseThrow { IllegalArgumentException("User not found") }

        val cookie = jwtUtils.generateJwtCookie(user.email)
        return Pair(userMapper.toAuthResponse(user), cookie)
    }

    override fun logout(): ResponseCookie = jwtUtils.generateCleanJwtCookie()

    override fun getCurrentUser(email: String): AuthResponse {
        val user = userRepository.findByEmail(email)
            .orElseThrow { IllegalArgumentException("User not found") }
        return userMapper.toAuthResponse(user)
    }
}
