package com.brobarber.backend.controller

import com.brobarber.backend.dto.request.BarberRequest
import com.brobarber.backend.dto.response.BarberResponse
import com.brobarber.backend.service.BarberService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/barbers")
class BarberController(
    private val barberService: BarberService
) {

    @PostMapping
    @PreAuthorize("hasRole('SHOP_OWNER')")
    fun create(
        @Valid @RequestBody request: BarberRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<BarberResponse> {
        val response = barberService.create(request, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<BarberResponse> {
        val response = barberService.getById(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/shop/{shopId}")
    fun getByShop(@PathVariable shopId: Long): ResponseEntity<List<BarberResponse>> {
        val response = barberService.getByShop(shopId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/shop/{shopId}/active")
    fun getActiveByShop(@PathVariable shopId: Long): ResponseEntity<List<BarberResponse>> {
        val response = barberService.getActiveByShop(shopId)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: BarberRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<BarberResponse> {
        val response = barberService.update(id, request, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    fun toggleActive(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<BarberResponse> {
        val response = barberService.toggleActive(id, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    fun delete(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Void> {
        barberService.delete(id, userDetails.username)
        return ResponseEntity.noContent().build()
    }
}
