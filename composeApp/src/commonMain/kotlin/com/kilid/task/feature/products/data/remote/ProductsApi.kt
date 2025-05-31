package com.kilid.task.feature.products.data.remote

import com.kilid.task.data.remote.makeHttpRequestSafely
import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.domain.model.Result
import com.kilid.task.domain.model.map
import com.kilid.task.feature.products.data.model.ProductDTO
import com.kilid.task.feature.products.domain.api.IProductsApi
import com.kilid.task.feature.products.domain.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

private const val TAG = "ProductsApi"
private const val PRODUCTS_ENDPOINT = "/products"
private const val CATEGORY_ENDPOINT = "$PRODUCTS_ENDPOINT/category/"

class ProductsApi(
    private val httpClient: HttpClient, private val ioDispatcher: CoroutineDispatcher
) : IProductsApi {
    override suspend fun fetchProducts(
        category: ProductCategory?
    ): Result<List<Product>> = withContext(ioDispatcher) {
        val endpoint =
            if (category == null) PRODUCTS_ENDPOINT else "$CATEGORY_ENDPOINT${category.rawName}"

        return@withContext makeHttpRequestSafely<List<ProductDTO>>(TAG) { httpClient.get(endpoint) }
            .map { list -> list.map { productDTO -> productDTO.toDomainModel() } }
    }
}