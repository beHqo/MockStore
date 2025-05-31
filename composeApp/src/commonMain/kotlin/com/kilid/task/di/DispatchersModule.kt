package com.kilid.task.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_DISPATCHER = "MainDispatcher"
const val DEFAULT_DISPATCHER = "DefaultDispatcher"
const val IO_DISPATCHER = "IODispatcher"

val dispatchersModule = module {
    factory(named(MAIN_DISPATCHER)) { Dispatchers.Main }
    factory(named(DEFAULT_DISPATCHER)) { Dispatchers.Default }
    factory(named(IO_DISPATCHER)) { Dispatchers.IO }
}