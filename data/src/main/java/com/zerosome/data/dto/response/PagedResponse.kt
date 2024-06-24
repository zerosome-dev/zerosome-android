package com.zerosome.data.dto.response

data class PagedResponse<out T>(
    val content: List<T>,
    val limit: Int
)