package com.brobarber.backend.service

import com.brobarber.backend.dto.request.QueueRequest
import com.brobarber.backend.dto.response.QueueResponse

interface QueueService {
    fun joinQueue(request: QueueRequest, clientEmail: String): QueueResponse
    fun getById(id: Long): QueueResponse
    fun getByShop(shopId: Long): List<QueueResponse>
    fun getWaitingByShop(shopId: Long): List<QueueResponse>
    fun getClientActiveQueues(clientEmail: String): List<QueueResponse>
    fun startSession(queueId: Long, barberEmail: String): QueueResponse
    fun finishSession(queueId: Long, barberEmail: String): QueueResponse
    fun skipQueue(queueId: Long, barberEmail: String): QueueResponse
    fun cancelQueue(queueId: Long, clientEmail: String): QueueResponse
    fun getBarberDailyCount(barberId: Long): Long
}
