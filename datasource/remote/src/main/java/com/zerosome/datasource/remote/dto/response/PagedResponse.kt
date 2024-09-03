package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResponse<out T>(
    @SerialName("content")
    val content: List<T>,
    @SerialName("limit")
    val limit: Int,
    @SerialName("offset")
    val offset: Int,
)