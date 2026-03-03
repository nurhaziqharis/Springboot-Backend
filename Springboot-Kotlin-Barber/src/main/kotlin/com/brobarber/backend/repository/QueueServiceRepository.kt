package com.brobarber.backend.repository

import com.brobarber.backend.entity.QueueService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QueueServiceRepository : JpaRepository<QueueService, Long> {

    fun findAllByQueueId(queueId: Long): List<QueueService>
}
