package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "shop_galleries", indexes = [
    Index(name = "idx_gallery_shop", columnList = "shop_id")
])
class ShopGallery(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Barbershop,

    @Column(name = "image_url", nullable = false, length = 500)
    var imageUrl: String,

    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var metadata: Map<String, Any>? = null

) : BaseEntity()
