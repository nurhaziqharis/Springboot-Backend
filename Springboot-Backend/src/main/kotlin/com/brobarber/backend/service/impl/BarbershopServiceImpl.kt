package com.brobarber.backend.service.impl

import com.brobarber.backend.dto.request.BarbershopRequest
import com.brobarber.backend.dto.response.BarbershopResponse
import com.brobarber.backend.mapper.BarbershopMapper
import com.brobarber.backend.repository.BarbershopRepository
import com.brobarber.backend.repository.RatingRepository
import com.brobarber.backend.repository.UserRepository
import com.brobarber.backend.service.BarbershopService
import org.springframework.stereotype.Service

@Service
class BarbershopServiceImpl(
    private val barbershopRepository: BarbershopRepository,
    private val userRepository: UserRepository,
    private val ratingRepository: RatingRepository,
    private val barbershopMapper: BarbershopMapper
) : BarbershopService {

    override fun create(request: BarbershopRequest, ownerEmail: String): BarbershopResponse {
        val owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow { IllegalArgumentException("Owner not found") }
        val entity = barbershopMapper.toEntity(request, owner)
        val saved = barbershopRepository.save(entity)
        return barbershopMapper.toResponse(saved)
    }

    override fun getById(id: Long): BarbershopResponse {
        val entity = barbershopRepository.findById(id)
            .orElseThrow { NoSuchElementException("Barbershop not found with id: $id") }
        val avgRating = ratingRepository.averageScoreByShopId(id)
        return barbershopMapper.toResponse(entity, avgRating)
    }

    override fun getAll(): List<BarbershopResponse> =
        barbershopRepository.findAll().map { barbershopMapper.toResponse(it) }

    override fun getOpenShops(): List<BarbershopResponse> =
        barbershopRepository.findByIsOpenTrue().map { barbershopMapper.toResponse(it) }

    override fun getByOwner(ownerEmail: String): List<BarbershopResponse> {
        val owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow { IllegalArgumentException("Owner not found") }
        return barbershopRepository.findByOwnerId(owner.id).map { barbershopMapper.toResponse(it) }
    }

    override fun update(id: Long, request: BarbershopRequest, ownerEmail: String): BarbershopResponse {
        val entity = barbershopRepository.findById(id)
            .orElseThrow { NoSuchElementException("Barbershop not found with id: $id") }
        val owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow { IllegalArgumentException("Owner not found") }

        if (entity.owner.id != owner.id) {
            throw IllegalAccessException("You are not the owner of this barbershop")
        }

        barbershopMapper.updateEntity(entity, request)
        val saved = barbershopRepository.save(entity)
        return barbershopMapper.toResponse(saved)
    }

    override fun delete(id: Long, ownerEmail: String) {
        val entity = barbershopRepository.findById(id)
            .orElseThrow { NoSuchElementException("Barbershop not found with id: $id") }
        val owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow { IllegalArgumentException("Owner not found") }

        if (entity.owner.id != owner.id) {
            throw IllegalAccessException("You are not the owner of this barbershop")
        }

        barbershopRepository.delete(entity)
    }
}
