package com.zerosome.domain.repository

import com.zerosome.domain.model.Review
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun createReview(
        productId: Int,
        productReviewText: String,
        productReviewScore: Int
    ): Boolean

    fun getReview(
        productId: Int,
        cursor: Int?,
    ): Flow<NetworkResult<List<Review>>>
}