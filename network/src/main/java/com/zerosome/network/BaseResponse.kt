package com.zerosome.network

import kotlinx.serialization.SerialName

data class BaseResponse<T>(
    @SerialName("code")
    val code: String,
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: T
)