package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrandResponse(
    @SerialName("brandCode")
    val brandCode: String,
    @SerialName("brandName")
    val brandName: String,
)