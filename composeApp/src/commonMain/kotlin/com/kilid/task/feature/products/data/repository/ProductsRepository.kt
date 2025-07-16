package com.kilid.task.feature.products.data.repository

import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.domain.model.Result
import com.kilid.task.feature.products.domain.api.IProductsApi
import com.kilid.task.feature.products.domain.model.Product
import com.kilid.task.feature.products.domain.repository.IProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductsRepository(private val productsApi: IProductsApi) : IProductsRepository {
    override fun fetchProducts(category: ProductCategory?): Flow<Result<List<Product>>> = flow {
        emit(Result.Loading)

        emit(productsApi.fetchProducts(category))
    }

    override suspend fun fetchProductDetails(productId: Int): Result<Product> =
        productsApi.fetchProductDetails(productId)
}