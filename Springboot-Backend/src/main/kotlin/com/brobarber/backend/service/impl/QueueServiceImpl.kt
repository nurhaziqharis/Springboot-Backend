package com.brobarber.backend.service.impl

import com.brobarber.backend.dto.request.QueueRequest
import com.brobarber.backend.dto.response.QueueResponse
import com.brobarber.backend.enums.QueueStatus
import com.brobarber.backend.mapper.QueueMapper
import com.brobarber.backend.model.Queue
import com.brobarber.backend.repository.BarberRepository
import com.brobarber.backend.repository.BarbershopRepository
import com.brobarber.backend.repository.QueueRepository
import com.brobarber.backend.repository.UserRepository
import com.brobarber.backend.service.QueueService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class QueueServiceImpl(
    private val queueRepository: QueueRepository,
    private val barbershopRepository: BarbershopRepository,
    private val barberRepository: BarberRepository,
    private val userRepository: UserRepository,
    private val queueMapper: QueueMapper
) : QueueService {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    override fun joinQueue(request: QueueRequest, clientEmail: String): QueueResponse {
        val client = userRepository.findByEmail(clientEmail)
            .orElseThrow { IllegalArgumentException("Client not found") }
        val shop = barbershopRepository.findById(request.shopId)
            .orElseThrow { NoSuchElementException("Barbershop not found") }

        val barber = request.barberId?.let {
            barberRepository.findById(it).orElseThrow { NoSuchElementException("Barber not found") }
        }

        val datePrefix = LocalDate.now().format(dateFormatter)
        val count = queueRepository.countByShopIdAndQueueNumberStartingWith(shop.id, datePrefix)
        val queueNumber = "$datePrefix${String.format("%04d", count + 1)}"

        val queue = Queue(
            shop = shop,
            client = client,
            barber = barber,
            queueNumber = queueNumber,
            status = QueueStatus.WAITING,
            joinedAt = LocalDateTime.now()
        )

        val saved = queueRepository.save(queue)
        return queueMapper.toResponse(saved)
    }

    override fun getById(id: Long): QueueResponse {
        val queue = queueRepository.findById(id)
            .orElseThrow { NoSuchElementException("Queue not found with id: $id") }
        return queueMapper.toResponse(queue)
    }

    override fun getByShop(shopId: Long): List<QueueResponse> =
        queueRepository.findByShopIdOrderByJoinedAtAsc(shopId).map { queueMapper.toResponse(it) }

    override fun getWaitingByShop(shopId: Long): List<QueueResponse> =
        queueRepository.findByShopIdAndStatus(shopId, QueueStatus.WAITING).map { queueMapper.toResponse(it) }

    override fun getClientActiveQueues(clientEmail: String): List<QueueResponse> {
        val client = userRepository.findByEmail(clientEmail)
            .orElseThrow { IllegalArgumentException("Client not found") }
        return queueRepository.findByClientIdAndStatus(client.id, QueueStatus.WAITING)
            .plus(queueRepository.findByClientIdAndStatus(client.id, QueueStatus.IN_PROGRESS))
            .map { queueMapper.toResponse(it) }
    }

    override fun startSession(queueId: Long, barberEmail: String): QueueResponse {
        val queue = queueRepository.findById(queueId)
            .orElseThrow { NoSuchElementException("Queue not found") }
        val barberUser = userRepository.findByEmail(barberEmail)
            .orElseThrow { IllegalArgumentException("User not found") }
        val barber = barberRepository.findByUserId(barberUser.id)
            ?: throw IllegalArgumentException("Barber profile not found")

        queue.barber = barber
        queue.status = QueueStatus.IN_PROGRESS
        queue.startedAt = LocalDateTime.now()

        val saved = queueRepository.save(queue)
        return queueMapper.toResponse(saved)
    }

    override fun finishSession(queueId: Long, barberEmail: String): QueueResponse {
        val queue = queueRepository.findById(queueId)
            .orElseThrow { NoSuchElementException("Queue not found") }

        queue.status = QueueStatus.FINISHED
        queue.finishedAt = LocalDateTime.now()

        val saved = queueRepository.save(queue)
        return queueMapper.toResponse(saved)
    }

    override fun skipQueue(queueId: Long, barberEmail: String): QueueResponse {
        val queue = queueRepository.findById(queueId)
            .orElseThrow { NoSuchElementException("Queue not found") }

        queue.status = QueueStatus.CANCELLED

        val saved = queueRepository.save(queue)
        return queueMapper.toResponse(saved)
    }

    override fun cancelQueue(queueId: Long, clientEmail: String): QueueResponse {
        val queue = queueRepository.findById(queueId)
            .orElseThrow { NoSuchElementException("Queue not found") }
        val client = userRepository.findByEmail(clientEmail)
            .orElseThrow { IllegalArgumentException("Client not found") }

        if (queue.client.id != client.id) {
            throw IllegalAccessException("You can only cancel your own queue")
        }

        queue.status = QueueStatus.CANCELLED
        val saved = queueRepository.save(queue)
        return queueMapper.toResponse(saved)
    }

    override fun getBarberDailyCount(barberId: Long): Long =
        queueRepository.countFinishedTodayByBarberId(barberId)
}
