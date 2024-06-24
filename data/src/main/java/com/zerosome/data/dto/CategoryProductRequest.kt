package com.zerosome.data.dto

import kotlinx.serialization.SerialName

data class CategoryProductRequest(
    @SerialName("d2CategoryCode")
    val categoryDepth2Code: Int,
    @SerialName("orderType")
    val orderType: String,
    @SerialName("brandList")
    val brandList: List<String>,
    @SerialName("zeroCtgList")
    val zeroTagList: List<String>
)