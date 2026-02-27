package com.brobarber.backend.service

import com.brobarber.backend.dto.request.LoginRequest
import com.brobarber.backend.dto.request.RegisterRequest
import com.brobarber.backend.dto.response.AuthResponse
import org.springframework.http.ResponseCookie

interface AuthService {
    fun register(request: RegisterRequest): AuthResponse
    fun login(request: LoginRequest): Pair<AuthResponse, ResponseCookie>
    fun logout(): ResponseCookie
    fun getCurrentUser(email: String): AuthResponse
}
