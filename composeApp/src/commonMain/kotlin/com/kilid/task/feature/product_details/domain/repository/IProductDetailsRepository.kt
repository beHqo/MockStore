package com.kilid.task.feature.product_details.domain.repository

import com.kilid.task.domain.model.Result
import com.kilid.task.feature.product_details.domain.model.ProductDetails

interface IProductDetailsRepository {
    suspend fun fetchProductDetails(id: Int): Result<ProductDetails>
}
