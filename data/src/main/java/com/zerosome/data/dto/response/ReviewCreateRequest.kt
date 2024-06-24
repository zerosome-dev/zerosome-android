package com.zerosome.data.dto.response

import kotlinx.serialization.SerialName

data class ReviewCreateRequest(
    @SerialName("productId")
    val productId: Int,
    @SerialName("rating")
    val rating: Float,
    @SerialName("content")
    val content: String? = null,
)