package com.zerosome.domain

import com.zerosome.core.constants.ClientExceptions

sealed interface NetworkResult<out T> {
    data object Loading: NetworkResult<Nothing>

    data class Success<T>(val data: T): NetworkResult<T>

    data class Error(val error: ClientExceptions): NetworkResult<Nothing>
}