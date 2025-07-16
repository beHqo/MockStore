package com.kilid.task.feature.products.di

import com.kilid.task.di.IO_DISPATCHER
import com.kilid.task.feature.products.data.remote.ProductsApi
import com.kilid.task.feature.products.data.repository.ProductsRepository
import com.kilid.task.feature.products.domain.api.IProductsApi
import com.kilid.task.feature.products.domain.interactor.FetchProductDetailsUseCase
import com.kilid.task.feature.products.domain.interactor.FetchProductsUseCase
import com.kilid.task.feature.products.domain.repository.IProductsRepository
import com.kilid.task.feature.products.presentation.product_details.ProductDetailsViewModel
import com.kilid.task.feature.products.presentation.products.ProductsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val productsModule = module {
    single<IProductsApi> {
        ProductsApi(httpClient = get(), ioDispatcher = get(named(IO_DISPATCHER)))
    }

    singleOf(::ProductsRepository) { bind<IProductsRepository>() }

    factoryOf(::FetchProductsUseCase)

    factoryOf(::FetchProductDetailsUseCase)

    viewModelOf(::ProductsViewModel)

    viewModelOf(::ProductDetailsViewModel)
}