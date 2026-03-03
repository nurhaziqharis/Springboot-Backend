package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "service_categories", indexes = [
    Index(name = "idx_category_shop", columnList = "shop_id")
])
class ServiceCategory(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    var shop: Barbershop,

    @Column(nullable = false, length = 100)
    var name: String

) : BaseEntity() {

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var services: MutableList<Service> = mutableListOf()
}
