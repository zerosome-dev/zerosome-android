package com.zerosome.data.dto.response

import kotlinx.serialization.SerialName

data class ZeroCategoryResponse(
    @SerialName("zeroCtgCode")
    val zeroCategoryCode: String,
    @SerialName("zeroCtgName")
    val zeroCategoryName: String,
)