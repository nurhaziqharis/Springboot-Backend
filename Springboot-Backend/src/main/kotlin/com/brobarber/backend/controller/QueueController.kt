package com.brobarber.backend.controller

import com.brobarber.backend.dto.request.QueueRequest
import com.brobarber.backend.dto.response.QueueResponse
import com.brobarber.backend.service.QueueService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/queues")
class QueueController(
    private val queueService: QueueService
) {

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    fun joinQueue(
        @Valid @RequestBody request: QueueRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<QueueResponse> {
        val response = queueService.joinQueue(request, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<QueueResponse> {
        val response = queueService.getById(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/shop/{shopId}")
    fun getByShop(@PathVariable shopId: Long): ResponseEntity<List<QueueResponse>> {
        val response = queueService.getByShop(shopId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/shop/{shopId}/waiting")
    fun getWaitingByShop(@PathVariable shopId: Long): ResponseEntity<List<QueueResponse>> {
        val response = queueService.getWaitingByShop(shopId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/my-queues")
    @PreAuthorize("hasRole('CLIENT')")
    fun getMyActiveQueues(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<List<QueueResponse>> {
        val response = queueService.getClientActiveQueues(userDetails.username)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/start")
    @PreAuthorize("hasRole('BARBER')")
    fun startSession(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<QueueResponse> {
        val response = queueService.startSession(id, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/finish")
    @PreAuthorize("hasRole('BARBER')")
    fun finishSession(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<QueueResponse> {
        val response = queueService.finishSession(id, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/skip")
    @PreAuthorize("hasRole('BARBER')")
    fun skipQueue(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<QueueResponse> {
        val response = queueService.skipQueue(id, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasRole('CLIENT')")
    fun cancelQueue(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<QueueResponse> {
        val response = queueService.cancelQueue(id, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/barber/{barberId}/daily-count")
    @PreAuthorize("hasAnyRole('SHOP_OWNER', 'BARBER')")
    fun getBarberDailyCount(@PathVariable barberId: Long): ResponseEntity<Map<String, Long>> {
        val count = queueService.getBarberDailyCount(barberId)
        return ResponseEntity.ok(mapOf("count" to count))
    }
}
