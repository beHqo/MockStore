package com.kilid.task.feature.product_details.di

import com.kilid.task.di.IO_DISPATCHER
import com.kilid.task.feature.product_details.data.remote.ProductDetailsApi
import com.kilid.task.feature.product_details.data.repository.ProductDetailsRepository
import com.kilid.task.feature.product_details.domain.api.IProductDetailsApi
import com.kilid.task.feature.product_details.domain.interactor.FetchProductDetailsUseCase
import com.kilid.task.feature.product_details.domain.repository.IProductDetailsRepository
import com.kilid.task.feature.product_details.presentation.ProductDetailsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val productDetailsModule = module {
    single<IProductDetailsApi> {
        ProductDetailsApi(httpClient = get(), ioDispatcher = get(named(IO_DISPATCHER)))
    }

    singleOf(::ProductDetailsRepository) { bind<IProductDetailsRepository>() }

    factoryOf(::FetchProductDetailsUseCase)

    viewModelOf(::ProductDetailsViewModel)
}