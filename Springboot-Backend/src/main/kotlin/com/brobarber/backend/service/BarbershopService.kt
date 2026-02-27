package com.brobarber.backend.service

import com.brobarber.backend.dto.request.BarbershopRequest
import com.brobarber.backend.dto.response.BarbershopResponse

interface BarbershopService {
    fun create(request: BarbershopRequest, ownerEmail: String): BarbershopResponse
    fun getById(id: Long): BarbershopResponse
    fun getAll(): List<BarbershopResponse>
    fun getOpenShops(): List<BarbershopResponse>
    fun getByOwner(ownerEmail: String): List<BarbershopResponse>
    fun update(id: Long, request: BarbershopRequest, ownerEmail: String): BarbershopResponse
    fun delete(id: Long, ownerEmail: String)
}
