package com.kilid.task.feature.product_details.domain.api

import com.kilid.task.domain.model.Result
import com.kilid.task.feature.product_details.domain.model.ProductDetails

interface IProductDetailsApi {
    suspend fun fetchProductDetails(productId: Int): Result<ProductDetails>
}