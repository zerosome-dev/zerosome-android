package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CafeResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("d1CategoryId")
    val categoryDepth1Id: String,
    @SerialName("d2CategoryId")
    val categoryDepth2Id: String,
    @SerialName("name")
    val name: String,
    @SerialName("brand")
    val brand: String,
    @SerialName("review")
    val rating: Float? = null,
    @SerialName("reviewCnt")
    val reviewCount: Int
)