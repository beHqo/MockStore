package com.kilid.task.data.remote

import com.kilid.task.domain.Log
import com.kilid.task.domain.model.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import okio.IOException

suspend inline fun <reified T> makeHttpRequestSafely(
    logTag: String, noinline block: suspend () -> HttpResponse
): Result<T> = try {
    val response = block()

    Log.i(logTag, "response status code: ${response.status}")

    Result.Success(response.body<T>())
} catch (e: ResponseException) {
    Log.e(logTag, "Failed to make http request", e)

    Result.Error(e.message ?: "Unknown error")
} catch (e: SerializationException) {
    Log.e(logTag, "Failed to deserialize data", e)

    Result.Error(e.message ?: "Unknown error")
} catch (e: ConnectTimeoutException) {
    Log.e(logTag, "HTTP request timed out", e)

    Result.Error(e.message ?: "Unknown error")
} catch (e: IOException) {
    Log.e(logTag, "Failed to make HTTP request due to IO error", e)

    Result.Error(e.message ?: "Unknown error")
}