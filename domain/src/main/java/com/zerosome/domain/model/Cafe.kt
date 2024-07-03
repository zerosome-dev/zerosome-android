package com.zerosome.domain.model

data class Cafe(
    val id: Int,
    val image: String,
    val categoryDepth1Id: Int,
    val categoryDepth2Id: Int,
    val name: String,
    val brand: String,
    val rating: Float,
    val reviewCount: Int
)