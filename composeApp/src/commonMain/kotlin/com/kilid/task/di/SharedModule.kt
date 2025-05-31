package com.kilid.task.di

import com.kilid.task.data.remote.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {
    singleOf(HttpClientFactory::create)
}