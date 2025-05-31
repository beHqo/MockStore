package com.kilid.task.domain.model

sealed interface Result<out D> {
    data class Success<out D>(val data: D) : Result<D>
    data class Error(val message: String) : Result<Nothing>
    object Loading : Result<Nothing>
}

/**
 * If `this` is a `Success<D>`, apply [transform] to its data and wrap in `Success<R>`.
 * Otherwise (Loading or Error), return the same instance (typed as `Result<R>`).
 */
inline fun <D, R> Result<D>.map(transform: (D) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> this
    Result.Loading -> Result.Loading
}