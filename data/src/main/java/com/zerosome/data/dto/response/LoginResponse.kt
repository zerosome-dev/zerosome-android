package com.zerosome.data.dto.response

data class LoginResponse(
    val isMember: Boolean,
    val token: TokenResponse? = null
)