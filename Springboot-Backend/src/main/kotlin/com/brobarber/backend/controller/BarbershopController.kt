package com.brobarber.backend.controller

import com.brobarber.backend.dto.request.BarbershopRequest
import com.brobarber.backend.dto.response.BarbershopResponse
import com.brobarber.backend.service.BarbershopService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/barbershops")
class BarbershopController(
    private val barbershopService: BarbershopService
) {

    @PostMapping
    @PreAuthorize("hasRole('SHOP_OWNER')")
    fun create(
        @Valid @RequestBody request: BarbershopRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<BarbershopResponse> {
        val response = barbershopService.create(request, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<BarbershopResponse> {
        val response = barbershopService.getById(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<BarbershopResponse>> {
        val response = barbershopService.getAll()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/open")
    fun getOpenShops(): ResponseEntity<List<BarbershopResponse>> {
        val response = barbershopService.getOpenShops()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/my-shops")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    fun getMyShops(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<List<BarbershopResponse>> {
        val response = barbershopService.getByOwner(userDetails.username)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: BarbershopRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<BarbershopResponse> {
        val response = barbershopService.update(id, request, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    fun delete(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Void> {
        barbershopService.delete(id, userDetails.username)
        return ResponseEntity.noContent().build()
    }
}
