package com.brobarber.backend.repository

import com.brobarber.backend.entity.ShopGallery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopGalleryRepository : JpaRepository<ShopGallery, Long> {

    fun findAllByShopId(shopId: Long): List<ShopGallery>

    fun deleteByIdAndShopId(id: Long, shopId: Long): Int
}
