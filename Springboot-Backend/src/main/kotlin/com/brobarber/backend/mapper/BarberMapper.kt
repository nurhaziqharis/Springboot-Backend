package com.brobarber.backend.mapper

import com.brobarber.backend.dto.response.BarberResponse
import com.brobarber.backend.model.Barber
import org.springframework.stereotype.Component

@Component
class BarberMapper {

    fun toResponse(entity: Barber): BarberResponse = BarberResponse(
        id = entity.id,
        userId = entity.user.id,
        shopId = entity.shop.id,
        name = entity.name,
        isActive = entity.isActive
    )
}
