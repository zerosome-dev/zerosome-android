package com.zerosome.domain.repository

import com.zerosome.domain.model.Review

interface ReviewRepository {
    suspend fun createReview(
        productId: Int,
        productReviewText: String,
        productReviewScore: Int
    ): Boolean

    suspend fun getReview(
        productId: Int,
        cursor: Int?,
    ): List<Review>
}