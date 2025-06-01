package com.kilid.task.feature.product_details.data.model

import com.kilid.task.data.remote.model.ProductRatingDTO
import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.feature.product_details.domain.model.ProductDetails
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsDTO(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: ProductRatingDTO
) {
    fun toDomainModel() = ProductDetails(
        id = id,
        title = title,
        price = price,
        description = description,
        category = ProductCategory.fromRaw(category),
        image = image,
        rating = rating.toDomainModel()
    )
}