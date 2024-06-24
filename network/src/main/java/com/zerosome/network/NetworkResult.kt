package com.zerosome.network

sealed interface NetworkResult<out T> {
    data object Loading: NetworkResult<Nothing>

    data class Success<T>(val data: T): NetworkResult<T>

    data class Error(val error: NetworkError): NetworkResult<Nothing>
}