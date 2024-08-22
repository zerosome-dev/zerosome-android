package com.zerosome.domain.repository

interface ReviewRepository {
    suspend fun createReview(
        productId: Int,
        productReviewText: String,
        productReviewScore: Int
    ): Boolean
}