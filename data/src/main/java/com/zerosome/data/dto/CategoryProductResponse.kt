package com.zerosome.data.dto

import kotlinx.serialization.SerialName

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
    @SerialName("review")
    val review: Int,
    @SerialName("reviewCnt")
    val reviewCount: Int
)