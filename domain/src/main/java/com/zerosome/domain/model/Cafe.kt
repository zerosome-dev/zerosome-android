package com.zerosome.domain.model

data class Cafe(
    val id: Int,
    val image: String,
    val categoryDepth1Id: String,
    val categoryDepth2Id: String,
    val name: String,
    val brand: String,
    val rating: Float? = null,
    val reviewCount: Int
)