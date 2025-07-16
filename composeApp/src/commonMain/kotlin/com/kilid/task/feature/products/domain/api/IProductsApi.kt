package com.kilid.task.feature.products.domain.api

import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.domain.model.Result
import com.kilid.task.feature.products.domain.model.Product

interface IProductsApi {
    suspend fun fetchProducts(category: ProductCategory?): Result<List<Product>>
    suspend fun fetchProductDetails(productId: Int): Result<Product>
}