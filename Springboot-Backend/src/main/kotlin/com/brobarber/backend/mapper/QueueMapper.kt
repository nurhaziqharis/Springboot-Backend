package com.brobarber.backend.mapper

import com.brobarber.backend.dto.response.QueueResponse
import com.brobarber.backend.model.Queue
import org.springframework.stereotype.Component

@Component
class QueueMapper {

    fun toResponse(entity: Queue): QueueResponse = QueueResponse(
        id = entity.id,
        shopId = entity.shop.id,
        clientId = entity.client.id,
        clientName = entity.client.name,
        barberId = entity.barber?.id,
        barberName = entity.barber?.name,
        queueNumber = entity.queueNumber,
        status = entity.status.name,
        joinedAt = entity.joinedAt,
        startedAt = entity.startedAt,
        finishedAt = entity.finishedAt
    )
}
