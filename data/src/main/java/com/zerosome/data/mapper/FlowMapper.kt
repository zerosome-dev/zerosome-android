package com.zerosome.data.mapper

import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun<T, R> Flow<NetworkResult<T>>.mapToDomain(transpose: (T) -> R) = map {
    when (it) {
        is NetworkResult.Loading -> NetworkResult.Loading
        is NetworkResult.Success -> NetworkResult.Success(transpose(it.data))
        is NetworkResult.Error -> NetworkResult.Error(it.error)
    }
}