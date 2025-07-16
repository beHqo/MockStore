package com.kilid.task.feature.products.domain.repository

import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.domain.model.Result
import com.kilid.task.feature.products.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface IProductsRepository {
    fun fetchProducts(category: ProductCategory?): Flow<Result<List<Product>>>
    suspend fun fetchProductDetails(productId: Int): Result<Product>
}