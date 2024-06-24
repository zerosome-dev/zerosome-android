package com.zerosome.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.http.Url
import io.ktor.http.headers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun<T> HttpClient.safeGet(
    token: String? = null,
    url: String,
    builder: HttpRequestBuilder.() -> Unit = {}
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)
    val response = get(Url(url), builder.apply {
        token?.let {
            headers {
                append("Authorization", "Bearer $it")
            }
        }
    }).body<BaseResponse<T>>()
    if (response.status) {
        emit(NetworkResult.Success(response.data))
    } else {
        emit(NetworkResult.Error(NetworkError.from(response.code)))
    }
}

fun<T> HttpClient.safePost(
    token: String? = null,
    url: String,
    builder: HttpRequestBuilder.() -> Unit = {}
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)
    val response = get(Url(url), builder.apply {
        token?.let {
            headers {
                append("Authorization", "Bearer $it")
            }
        }
    }).body<BaseResponse<T>>()
    if (response.status) {
        emit(NetworkResult.Success(response.data))
    } else {
        emit(NetworkResult.Error(NetworkError.from(response.code)))
    }
}

fun HttpClient.safeDelete(
    token: String? = null,
    url: String,
    builder: HttpRequestBuilder.() -> Unit = {}
): Flow<NetworkResult<Unit>> = flow {
    emit(NetworkResult.Loading)
    val response = delete(Url(url), builder.apply {
        token?.let {
            headers {
                append("Authorization", "Bearer $it")
            }
        }
    }).body<BaseResponse<Unit>>()
    if (response.status) {
        emit(NetworkResult.Success(response.data))
    } else {
        emit(NetworkResult.Error(NetworkError.from(response.code)))
    }
}