package com.zerosome.datasource.remote.dto.response
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)