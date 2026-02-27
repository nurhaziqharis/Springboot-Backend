package com.brobarber.backend.mapper

import com.brobarber.backend.dto.request.BarbershopRequest
import com.brobarber.backend.dto.response.BarbershopResponse
import com.brobarber.backend.model.Barbershop
import com.brobarber.backend.model.User
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component
class BarbershopMapper {

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun toEntity(request: BarbershopRequest, owner: User): Barbershop = Barbershop(
        owner = owner,
        name = request.name,
        description = request.description,
        address = request.address,
        isOpen = request.isOpen,
        openingTime = request.openingTime?.let { LocalTime.parse(it, timeFormatter) },
        closingTime = request.closingTime?.let { LocalTime.parse(it, timeFormatter) }
    )

    fun toResponse(entity: Barbershop, averageRating: Double? = null): BarbershopResponse = BarbershopResponse(
        id = entity.id,
        ownerId = entity.owner.id,
        ownerName = entity.owner.name,
        name = entity.name,
        description = entity.description,
        address = entity.address,
        isOpen = entity.isOpen,
        openingTime = entity.openingTime?.format(timeFormatter),
        closingTime = entity.closingTime?.format(timeFormatter),
        averageRating = averageRating
    )

    fun updateEntity(entity: Barbershop, request: BarbershopRequest) {
        entity.name = request.name
        entity.description = request.description
        entity.address = request.address
        entity.isOpen = request.isOpen
        entity.openingTime = request.openingTime?.let { LocalTime.parse(it, timeFormatter) }
        entity.closingTime = request.closingTime?.let { LocalTime.parse(it, timeFormatter) }
    }
}
