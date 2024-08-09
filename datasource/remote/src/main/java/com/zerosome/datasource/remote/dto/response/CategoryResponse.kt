package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("d1CategoryCode")
    val d1CategoryCode: String,
    @SerialName("d1CategoryName")
    val d1CategoryName: String,
    @SerialName("d2Category")
    val d2Category: List<CategoryDepth2Response>
)