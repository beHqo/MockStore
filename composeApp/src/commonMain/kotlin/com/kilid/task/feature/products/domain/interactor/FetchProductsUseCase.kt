package com.kilid.task.feature.products.domain.interactor

import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.domain.model.Result
import com.kilid.task.feature.products.domain.model.Product
import com.kilid.task.feature.products.domain.repository.IProductsRepository
import kotlinx.coroutines.flow.Flow

class FetchProductsUseCase(private val repository: IProductsRepository) {
    operator fun invoke(productCategory: ProductCategory?): Flow<Result<List<Product>>> =
        repository.fetchProducts(productCategory)
}