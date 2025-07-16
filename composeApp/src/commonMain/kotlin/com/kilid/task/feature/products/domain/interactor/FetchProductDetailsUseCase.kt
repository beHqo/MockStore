package com.kilid.task.feature.products.domain.interactor

import com.kilid.task.domain.model.Result
import com.kilid.task.feature.products.domain.model.Product
import com.kilid.task.feature.products.domain.repository.IProductsRepository

class FetchProductDetailsUseCase(private val repository: IProductsRepository) {
    suspend operator fun invoke(id: Int): Result<Product> = repository.fetchProductDetails(id)
}