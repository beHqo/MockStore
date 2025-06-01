package com.kilid.task.feature.product_details.data.repository

import com.kilid.task.domain.model.Result
import com.kilid.task.feature.product_details.domain.api.IProductDetailsApi
import com.kilid.task.feature.product_details.domain.model.ProductDetails
import com.kilid.task.feature.product_details.domain.repository.IProductDetailsRepository

class ProductDetailsRepository(private val api: IProductDetailsApi) : IProductDetailsRepository {
    override suspend fun fetchProductDetails(id: Int): Result<ProductDetails> =
        api.fetchProductDetails(id)
}