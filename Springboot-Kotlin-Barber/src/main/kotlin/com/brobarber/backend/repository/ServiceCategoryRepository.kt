package com.brobarber.backend.repository

import com.brobarber.backend.entity.ServiceCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServiceCategoryRepository : JpaRepository<ServiceCategory, Long> {

    fun findAllByShopId(shopId: Long): List<ServiceCategory>
}
