package com.zerosome.datasource.remote.dto.response

data class LoginResponse(
    val isMember: Boolean,
    val token: TokenResponse? = null
)