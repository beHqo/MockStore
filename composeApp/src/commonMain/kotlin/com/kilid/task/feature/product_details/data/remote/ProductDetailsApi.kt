package com.kilid.task.feature.product_details.data.remote

import com.kilid.task.data.remote.makeHttpRequestSafely
import com.kilid.task.domain.model.Result
import com.kilid.task.domain.model.map
import com.kilid.task.feature.product_details.data.model.ProductDetailsDTO
import com.kilid.task.feature.product_details.domain.api.IProductDetailsApi
import com.kilid.task.feature.product_details.domain.model.ProductDetails
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

private const val TAG = "ProductDetailsApi"
private const val PRODUCTS_ENDPOINT = "/products/"


class ProductDetailsApi(
    private val httpClient: HttpClient, private val ioDispatcher: CoroutineDispatcher
) : IProductDetailsApi {
    override suspend fun fetchProductDetails(productId: Int): Result<ProductDetails> =
        withContext(ioDispatcher) {
            return@withContext makeHttpRequestSafely<ProductDetailsDTO>(TAG) {
                httpClient.get("$PRODUCTS_ENDPOINT$productId")
            }.map { dto -> dto.toDomainModel() }
        }
}