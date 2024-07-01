package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName

data class CafeResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("d1CategoryId")
    val categoryDepth1Id: Int,
    @SerialName("d2CategoryId")
    val categoryDepth2Id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("brand")
    val brand: String,
    @SerialName("rating")
    val rating: Float,
    @SerialName("reviewCnt")
    val reviewCount: Int
)