package com.zerosome.data

import com.zerosome.network.BaseResponse
import com.zerosome.network.NetworkError
import com.zerosome.network.ZSNetworkException

suspend fun <T, R, B : BaseResponse<R>> apiCall(
    block: suspend () -> B,
    transform: suspend (R?) -> T
): T {
    val currentResponse = block()
    return if (currentResponse.status.not()) {
        val networkException = ZSNetworkException(NetworkError.from(currentResponse.code))
        throw networkException.toClientException()
    } else {
        transform(currentResponse.data)
    }
}