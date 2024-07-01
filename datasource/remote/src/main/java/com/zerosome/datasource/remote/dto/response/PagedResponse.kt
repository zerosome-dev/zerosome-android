package com.zerosome.datasource.remote.dto.response

data class PagedResponse<out T>(
    val content: List<T>,
    val limit: Int
)