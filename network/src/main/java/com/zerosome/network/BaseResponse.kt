package com.zerosome.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("code")
    val code: String,
    @SerialName("status")
    val status: Boolean,
    @SerialName("data")
    val data: T? = null
)