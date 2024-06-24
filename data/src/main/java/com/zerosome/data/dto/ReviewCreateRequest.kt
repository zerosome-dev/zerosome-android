package com.zerosome.data.dto

import kotlinx.serialization.SerialName

data class ReviewCreateRequest(
    @SerialName("productId")
    val productId: Int,
    @SerialName("rating")
    val rating: Int,
    @SerialName("content")
    val content: String,
)