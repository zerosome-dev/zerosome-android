package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryProductResponse(
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
    val rating: Int,
    @SerialName("reviewCnt")
    val reviewCount: Int
)