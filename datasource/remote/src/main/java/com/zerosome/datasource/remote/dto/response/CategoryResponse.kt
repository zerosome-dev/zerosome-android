package com.zerosome.datasource.remote.dto.response

import com.zerosome.datasource.remote.dto.response.CategoryDepth2Response
import kotlinx.serialization.SerialName

data class CategoryResponse(
    @SerialName("d1CategoryCode")
    val d1CategoryCode: String,
    @SerialName("d1CategoryName")
    val d1CategoryName: String,
    @SerialName("d2Category")
    val d2Category: List<CategoryDepth2Response>
)