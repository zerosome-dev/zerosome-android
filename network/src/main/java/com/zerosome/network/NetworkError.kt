package com.zerosome.network

enum class NetworkError(private val errorCode: String, val code: Int) {
    INVALID_PARAMETER("REQ00", 400),
    UNAUTHORIZED("AUTHO00", 401),
    INVALID_CALL("REQ00", 403),
    API_FORBIDDEN("REQ01", 404),
    METHOD_NOT_ALLOWED("REQ02", 405),
    SYSTEM("SYS00", 500),
    UNKNOWN("UNKNOWN", 999);

    companion object {
        fun from(code: String): NetworkError
                = entries.find { error -> error.errorCode == code } ?: UNKNOWN
    }
}