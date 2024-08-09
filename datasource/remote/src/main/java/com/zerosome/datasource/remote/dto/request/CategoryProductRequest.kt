package com.zerosome.datasource.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryProductRequest(
    @SerialName("d2CategoryCode")
    val categoryDepth2Code: String,
    @SerialName("orderType")
    val orderType: String,
    @SerialName("brandList")
    val brandList: List<String>,
    @SerialName("zeroCtgList")
    val zeroTagList: List<String>
)