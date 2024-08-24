package com.zerosome.review

import com.zerosome.domain.repository.ReviewRepository
import com.zerosome.product.GetProductDetailUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getReviewUseCase: GetReviewUseCase
) {

    suspend operator fun invoke(productId: Int, text: String, score: Int) = reviewRepository.createReview(productId, productReviewText = text, productReviewScore = score).also {
        getProductDetailUseCase.refresh()
    }.also {
        getReviewUseCase.refresh()
    }
}