package com.zerosome.data.dto

import kotlinx.serialization.SerialName

data class CategoryDepth2Response(
    @SerialName("d2CategoryCode")
    val categoryCode: String,
    @SerialName("d2CategoryName")
    val categoryName: String,
    @SerialName("d2CategoryImage")
    val categoryImage: String? = null,
    @SerialName("noOptionYn")
    val optional: Boolean? = null
)