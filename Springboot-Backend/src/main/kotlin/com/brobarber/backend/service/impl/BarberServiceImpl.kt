package com.brobarber.backend.service.impl

import com.brobarber.backend.dto.request.BarberRequest
import com.brobarber.backend.dto.response.BarberResponse
import com.brobarber.backend.mapper.BarberMapper
import com.brobarber.backend.model.Barber
import com.brobarber.backend.repository.BarberRepository
import com.brobarber.backend.repository.BarbershopRepository
import com.brobarber.backend.repository.UserRepository
import com.brobarber.backend.service.BarberService
import org.springframework.stereotype.Service

@Service
class BarberServiceImpl(
    private val barberRepository: BarberRepository,
    private val barbershopRepository: BarbershopRepository,
    private val userRepository: UserRepository,
    private val barberMapper: BarberMapper
) : BarberService {

    override fun create(request: BarberRequest, ownerEmail: String): BarberResponse {
        val shop = barbershopRepository.findById(request.shopId)
            .orElseThrow { NoSuchElementException("Barbershop not found") }
        val owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow { IllegalArgumentException("Owner not found") }

        if (shop.owner.id != owner.id) {
            throw IllegalAccessException("You are not the owner of this barbershop")
        }

        val user = userRepository.findById(request.userId)
            .orElseThrow { NoSuchElementException("User not found") }

        val barber = Barber(
            user = user,
            shop = shop,
            name = request.name,
            isActive = request.isActive
        )

        val saved = barberRepository.save(barber)
        return barberMapper.toResponse(saved)
    }

    override fun getById(id: Long): BarberResponse {
        val barber = barberRepository.findById(id)
            .orElseThrow { NoSuchElementException("Barber not found with id: $id") }
        return barberMapper.toResponse(barber)
    }

    override fun getByShop(shopId: Long): List<BarberResponse> =
        barberRepository.findByShopId(shopId).map { barberMapper.toResponse(it) }

    override fun getActiveByShop(shopId: Long): List<BarberResponse> =
        barberRepository.findByShopIdAndIsActiveTrue(shopId).map { barberMapper.toResponse(it) }

    override fun update(id: Long, request: BarberRequest, ownerEmail: String): BarberResponse {
        val barber = barberRepository.findById(id)
            .orElseThrow { NoSuchElementException("Barber not found") }
        val owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow { IllegalArgumentException("Owner not found") }

        if (barber.shop.owner.id != owner.id) {
            throw IllegalAccessException("You are not the owner of this barbershop")
        }

        barber.name = request.name
        barber.isActive = request.isActive
        val saved = barberRepository.save(barber)
        return barberMapper.toResponse(saved)
    }

    override fun toggleActive(id: Long, ownerEmail: String): BarberResponse {
        val barber = barberRepository.findById(id)
            .orElseThrow { NoSuchElementException("Barber not found") }
        val owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow { IllegalArgumentException("Owner not found") }

        if (barber.shop.owner.id != owner.id) {
            throw IllegalAccessException("You are not the owner of this barbershop")
        }

        barber.isActive = !barber.isActive
        val saved = barberRepository.save(barber)
        return barberMapper.toResponse(saved)
    }

    override fun delete(id: Long, ownerEmail: String) {
        val barber = barberRepository.findById(id)
            .orElseThrow { NoSuchElementException("Barber not found") }
        val owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow { IllegalArgumentException("Owner not found") }

        if (barber.shop.owner.id != owner.id) {
            throw IllegalAccessException("You are not the owner of this barbershop")
        }

        barberRepository.delete(barber)
    }
}
