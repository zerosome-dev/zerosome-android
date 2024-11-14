package com.zerosome.data.repository

import com.zerosome.data.apiCall
import com.zerosome.data.mapper.domainModel
import com.zerosome.datasource.remote.service.ReviewService
import com.zerosome.domain.model.Review
import com.zerosome.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val service: ReviewService
) : ReviewRepository {

    override suspend fun createReview(
        productId: Int,
        productReviewText: String,
        productReviewScore: Int
    ): Boolean = apiCall(
        block = { service.createReview(productId, productReviewScore.toFloat(), productReviewText) },
        transform = {
            it != null
        }
    )

    override suspend fun getReview(productId: Int, cursor: Int?): List<Review> = apiCall(
        block = { service.getReviews(productId, cursor) },
        transform = { it?.map { data -> data.domainModel } ?: emptyList() }
    )
}