package com.zerosome.data.repository

import com.zerosome.datasource.remote.service.ReviewService
import com.zerosome.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val service: ReviewService
) : ReviewRepository {

    override suspend fun createReview(
        productId: Int,
        productReviewText: String,
        productReviewScore: Int
    ): Boolean = service.createReview(
            productId,
            productReviewScore.toFloat(),
            contents = productReviewText
        ).code == "SUCCESS"

}