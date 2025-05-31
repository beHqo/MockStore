package com.kilid.task.feature.products.data.model

import com.kilid.task.data.remote.model.ProductRatingDTO
import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.feature.products.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: ProductRatingDTO
) {
    fun toDomainModel() = Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = ProductCategory.Companion.fromRaw(category),
        image = image,
        rating = rating.toDomainModel()
    )
}