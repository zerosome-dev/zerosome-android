package com.zerosome.data.dto

data class PagedResponse<out T>(
    val content: List<T>,
    val limit: Int
)