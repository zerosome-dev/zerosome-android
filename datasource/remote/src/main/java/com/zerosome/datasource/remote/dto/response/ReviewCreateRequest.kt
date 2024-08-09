package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewCreateRequest(
    @SerialName("productId")
    val productId: Int,
    @SerialName("rating")
    val rating: Float,
    @SerialName("content")
    val content: String? = null,
)