package com.brobarber.backend.service

import com.brobarber.backend.dto.request.BarberRequest
import com.brobarber.backend.dto.response.BarberResponse

interface BarberService {
    fun create(request: BarberRequest, ownerEmail: String): BarberResponse
    fun getById(id: Long): BarberResponse
    fun getByShop(shopId: Long): List<BarberResponse>
    fun getActiveByShop(shopId: Long): List<BarberResponse>
    fun update(id: Long, request: BarberRequest, ownerEmail: String): BarberResponse
    fun toggleActive(id: Long, ownerEmail: String): BarberResponse
    fun delete(id: Long, ownerEmail: String)
}
