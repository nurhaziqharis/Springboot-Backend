package com.brobarber.backend.repository

import com.brobarber.backend.entity.Service
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ServiceRepository : JpaRepository<Service, Long> {

    fun findAllByShopId(shopId: Long): List<Service>

    fun findAllByCategoryId(categoryId: Long): List<Service>

    @Query("SELECT s FROM Service s WHERE s.shop.id = :shopId AND s.category.id = :categoryId")
    fun findAllByShopIdAndCategoryId(shopId: Long, categoryId: Long): List<Service>
}
