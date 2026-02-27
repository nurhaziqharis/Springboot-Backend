package com.brobarber.backend.controller

import com.brobarber.backend.dto.request.LoginRequest
import com.brobarber.backend.dto.request.RegisterRequest
import com.brobarber.backend.dto.response.AuthResponse
import com.brobarber.backend.dto.response.MessageResponse
import com.brobarber.backend.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        val response = authService.register(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        val (response, cookie) = authService.login(request)
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(response)
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<MessageResponse> {
        val cookie = authService.logout()
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(MessageResponse("Logged out successfully"))
    }

    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<AuthResponse> {
        val response = authService.getCurrentUser(userDetails.username)
        return ResponseEntity.ok(response)
    }
}
