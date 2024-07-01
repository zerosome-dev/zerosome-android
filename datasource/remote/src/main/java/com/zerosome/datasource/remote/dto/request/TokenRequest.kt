package com.zerosome.datasource.remote.dto.request

import kotlinx.serialization.SerialName

data class TokenRequest(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
)