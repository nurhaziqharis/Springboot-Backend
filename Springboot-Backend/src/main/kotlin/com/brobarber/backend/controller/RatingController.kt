package com.brobarber.backend.controller

import com.brobarber.backend.dto.request.RatingRequest
import com.brobarber.backend.dto.response.RatingResponse
import com.brobarber.backend.service.RatingService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ratings")
class RatingController(
    private val ratingService: RatingService
) {

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    fun create(
        @Valid @RequestBody request: RatingRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<RatingResponse> {
        val response = ratingService.create(request, userDetails.username)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/queue/{queueId}")
    fun getByQueue(@PathVariable queueId: Long): ResponseEntity<RatingResponse> {
        val response = ratingService.getByQueue(queueId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/shop/{shopId}")
    fun getByShop(@PathVariable shopId: Long): ResponseEntity<List<RatingResponse>> {
        val response = ratingService.getByShop(shopId)
        return ResponseEntity.ok(response)
    }
}
