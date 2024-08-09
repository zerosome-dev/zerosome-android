package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("isMember")
    val isMember: Boolean,
    @SerialName("token")
    val token: TokenResponse? = null
)