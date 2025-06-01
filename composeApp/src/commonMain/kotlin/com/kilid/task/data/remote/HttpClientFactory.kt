package com.kilid.task.data.remote

import com.kilid.task.domain.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json

private const val KTOR_CLIENT_TAG = "KTOR"
private const val URL = "https://fakestoreapi.com"

object HttpClientFactory {
    fun create(): HttpClient = HttpClient(createHttpEngine()) {
        engine { dispatcher = Dispatchers.IO }

        defaultRequest { url(URL) }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(KTOR_CLIENT_TAG, message)
                }
            }
        }

        expectSuccess = true

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, request ->
                val responseException =
                    exception as? ResponseException ?: return@handleResponseExceptionWithRequest
                val exceptionResponse = responseException.response
                val exceptionResponseStatus = exceptionResponse.status

                throw ResponseException(
                    response = exceptionResponse,
                    cachedResponseText = "#${exceptionResponseStatus.value}: ${exceptionResponseStatus.description}"
                )
            }
        }

        install(HttpTimeout) {
            connectTimeoutMillis = 40_000
            requestTimeoutMillis = 20_000
        }
    }
}