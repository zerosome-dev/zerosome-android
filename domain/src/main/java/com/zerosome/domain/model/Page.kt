package com.zerosome.domain.model

data class Page<out T>(
    val page: List<T>,
    val offset: Int,
    val limit: Int,
)