package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ZeroCategoryResponse(
    @SerialName("zeroCtgCode")
    val zeroCategoryCode: String,
    @SerialName("zeroCtgName")
    val zeroCategoryName: String,
)