package com.zerosome.datasource.remote.dto.request

import kotlinx.serialization.SerialName

data class JoinRequest(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("agreement_marketing")
    val marketingAgreed: Boolean,
)