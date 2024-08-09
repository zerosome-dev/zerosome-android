package com.zerosome.datasource.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinRequest(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("agreement_marketing")
    val marketingAgreed: Boolean,
)