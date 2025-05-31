package com.kilid.task.data.remote.model

import com.kilid.task.domain.model.ProductRating
import kotlinx.serialization.Serializable

@Serializable
data class ProductRatingDTO(val rate: Double, val count: Int) {
    fun toDomainModel() = ProductRating(rate, count)
}