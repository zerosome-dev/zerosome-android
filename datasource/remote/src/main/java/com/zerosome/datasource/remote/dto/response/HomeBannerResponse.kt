package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName

data class HomeBannerResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("url")
    val url: String? = null
)