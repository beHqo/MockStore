package com.kilid.task.data.remote

import com.kilid.task.domain.Log
import com.kilid.task.domain.model.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import okio.IOException

/**
 * Executes an HTTP request safely, capturing both the response and any errors, and returns a [Result] wrapper.
 *
 * This function is marked as `suspend` and `inline`, which allows:
 * 1. Suspending execution until the HTTP call completes.
 * 2. Inlining the call site, avoiding the overhead of a function call for each invocation.
 * 3. Using a reified type parameter [T], enabling us to call [HttpResponse.body] without needing a manual
 *    [Class] or [KClass] token for deserialization.
 *
 * @param T The expected type of the successful response body. This type must be serializable by the configured
 *          Ktor JSON serializer (e.g., kotlinx.serialization).
 * @param logTag A string tag used for logging. This helps identify logs originating from a particular API or feature.
 * @param block A suspending lambda that, when invoked, issues an HTTP request and returns an [HttpResponse].
 *              The caller provides this block, typically calling `httpClient.get(...)` or an equivalent Ktor request.
 *
 * @return A [Result] containing either:
 *         - [Result.Success] with a value of type [T], if the HTTP call succeeds and the response body deserializes correctly.
 *         - [Result.Error] with an error message, if any exception occurs during the request or deserialization.
 *
 * Internally, this function handles:
 * 1. Issuing the HTTP request by calling `block()`.
 * 2. Logging the HTTP status code on success.
 * 3. Deserializing the response body into type [T].
 * 4. Catching and distinguishing several common exception types.
 *
 * Each catch block logs an error with [Log.e] using [logTag], providing detailed stack trace information,
 * and then returns [Result.Error] with the exception’s message (or `"Unknown error"` if the message is null).
 */

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