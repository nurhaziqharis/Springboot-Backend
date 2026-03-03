package com.brobarber.backend.entity

import com.brobarber.backend.entity.base.BaseEntity
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hibernate.annotations.Type
import java.time.LocalDateTime

@Entity
@Table(name = "ratings", indexes = [
    Index(name = "idx_rating_queue", columnList = "queue_id"),
    Index(name = "idx_rating_score", columnList = "score")
])
class Rating(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id", nullable = false, unique = true)
    var queue: Queue,

    @Min(1)
    @Max(10)
    @Column(nullable = false)
    var score: Int,

    @Column(columnDefinition = "TEXT")
    var comment: String? = null,

    @Type(JsonBinaryType::class)
    @Column(name = "survey_responses", columnDefinition = "jsonb")
    var surveyResponses: Map<String, Any>? = null, // Cleanliness, Atmosphere, etc.

    @Column(name = "shop_reply", columnDefinition = "TEXT")
    var shopReply: String? = null,

    @Column(name = "replied_at")
    var repliedAt: LocalDateTime? = null

) : BaseEntity()
