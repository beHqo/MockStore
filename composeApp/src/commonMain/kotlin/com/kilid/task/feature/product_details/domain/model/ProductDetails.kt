package com.kilid.task.feature.product_details.domain.model

import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.domain.model.ProductRating

data class ProductDetails(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: ProductCategory,
    val image: String,
    val rating: ProductRating
)
