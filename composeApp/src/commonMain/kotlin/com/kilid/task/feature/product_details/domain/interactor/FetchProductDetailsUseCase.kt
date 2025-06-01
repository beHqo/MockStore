package com.kilid.task.feature.product_details.domain.interactor

import com.kilid.task.domain.model.Result
import com.kilid.task.feature.product_details.domain.model.ProductDetails
import com.kilid.task.feature.product_details.domain.repository.IProductDetailsRepository

class FetchProductDetailsUseCase(private val repository: IProductDetailsRepository) {
    suspend operator fun invoke(id: Int): Result<ProductDetails> =
        repository.fetchProductDetails(id)
}