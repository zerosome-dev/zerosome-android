package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDetailResponse(
    @SerialName("reviewId")
    val reviewId: Int,
    @SerialName("rating")
    val rating: Float,
    @SerialName("reviewContents")
    val reviewContents: String,
    @SerialName("regDate")
    val regDate: String, // CHANGE TO KOTLINX_DATETIME
    @SerialName("nickname")
    val nickname: String
)