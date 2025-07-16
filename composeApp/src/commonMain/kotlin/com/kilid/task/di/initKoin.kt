package com.kilid.task.di

import com.kilid.task.feature.products.di.productsModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, dispatchersModule, productsModule)
    }
}