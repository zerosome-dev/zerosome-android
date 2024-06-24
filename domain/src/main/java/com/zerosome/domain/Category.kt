package com.zerosome.domain

data class CategoryDepth1(
    val categoryCode: String,
    val categoryName: String,
    val categoryDepth2: List<CategoryDepth2>
)

data class CategoryDepth2(
    val categoryCode: String,
    val categoryName: String,
    val categoryImage: String? = null,
    val optional: Boolean = false
)