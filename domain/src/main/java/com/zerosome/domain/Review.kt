package com.zerosome.domain

data class Review(
    val reviewId: Int,
    val rating: Float,
    val reviewContents: String,
    val regDate: String, // CHANGE TO KOTLINX_DATETIME,
    val authorNickname: String
)