package com.kilid.task.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun createHttpEngine(): HttpClientEngine = Darwin.create()