package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    @SerialName("productId")
    val productId: Int,
    @SerialName("image")
    val image: String?,
    @SerialName("brandName")
    val brandName: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("rating")
    val rating: Float?,
    @SerialName("reviewCnt")
    val reviewCount: Int
)