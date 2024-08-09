package com.zerosome.main.detail

import com.zerosome.domain.model.RelatedProduct
import com.zerosome.domain.model.ReviewThumbnail
import com.zerosome.domain.model.Store

sealed interface ProductDetailUiModel {
    data class Introduction(val imageSrc: String, val brandName: String, val name: String, val reviewRating: Float, val reviewCount: Int): ProductDetailUiModel

    data object Nutrient: ProductDetailUiModel

    data class Stores(val onlineStore: List<Store.Online>, val offlineStore: List<Store.Offline>): ProductDetailUiModel

    data class Reviews(val reviewCount: Int, val rating: Float, val reviewList: List<ReviewThumbnail>): ProductDetailUiModel

    data object EmptyReview: ProductDetailUiModel

    data class SimilarProducts(val products: List<RelatedProduct>): ProductDetailUiModel
}