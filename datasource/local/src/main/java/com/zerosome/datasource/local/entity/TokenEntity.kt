package com.zerosome.datasource.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class TokenEntity(
    val accessToken: String,
    val refreshToken: String
)