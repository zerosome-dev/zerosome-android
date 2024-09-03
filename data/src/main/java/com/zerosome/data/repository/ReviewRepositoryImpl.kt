package com.zerosome.data.repository

import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.remote.service.ReviewService
import com.zerosome.domain.model.Review
import com.zerosome.domain.repository.ReviewRepository
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.flow.Flow
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

    override fun getReview(productId: Int, cursor: Int?): Flow<NetworkResult<List<Review>>> = safeCall{
        service.getReviews(productId, cursor)
    }.mapToDomain { it.map { item -> item.domainModel } }


}