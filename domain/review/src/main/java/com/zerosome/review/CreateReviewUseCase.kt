package com.zerosome.review

import com.zerosome.domain.repository.ReviewRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(productId: Int, text: String, score: Int) = reviewRepository.createReview(productId, productReviewText = text, productReviewScore = score)
}