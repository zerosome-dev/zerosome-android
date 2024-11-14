package com.zerosome.network

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions

enum class NetworkError(val errorCode: String, val code: Int) {
    INVALID_PARAMETER("REQ00", 400),
    UNAUTHORIZED("AUTHO00", 401),
    INVALID_CALL("REQ00", 403),
    API_FORBIDDEN("REQ01", 404),
    METHOD_NOT_ALLOWED("REQ02", 405),
    SYSTEM("SYS00", 500),
    UNKNOWN("UNKNOWN", 999);

    companion object {
        fun from(code: String): NetworkError =
            entries.find { error -> error.errorCode == code } ?: UNKNOWN
    }
}

class ZSNetworkException(val error: NetworkError) : Exception() {
    fun toClientException(): ClientExceptions = when (error) {
        NetworkError.INVALID_CALL,
        NetworkError.INVALID_PARAMETER,
        NetworkError.METHOD_NOT_ALLOWED,
        NetworkError.SYSTEM,
        NetworkError.API_FORBIDDEN,
        NetworkError.UNKNOWN -> ClientExceptions(clientErrors = ClientError.NETWORK_UNVALIDATED_INVALID)

        NetworkError.UNAUTHORIZED -> ClientExceptions(clientErrors = ClientError.AUTH_NOT_VALIDATED)
    }
}