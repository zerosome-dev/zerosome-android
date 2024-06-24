package com.zerosome.data.dto.response

import kotlinx.serialization.SerialName

data class BrandResponse(
    @SerialName("brandCode")
    val brandCode: String,
    @SerialName("brandName")
    val brandName: String,
)