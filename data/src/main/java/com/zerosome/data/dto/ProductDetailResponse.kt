package com.zerosome.data.dto

import kotlinx.serialization.SerialName

data class ProductDetailResponse(
    @SerialName("productId")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("brandName")
    val brandName: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("nutrientList")
    val nutrientList: List<NutrientResponse>

)

data class NutrientResponse(
    @SerialName("nutrientName")
    val nutrientName: String,
    @SerialName("servingPercent")
    val servingPercent: Int,
    @SerialName("amount")
    val amount: Int,
)

data class OfflineStoreResponse(
    @SerialName("storeCode")
    val storeCode: String,
    @SerialName("storeName")
    val storeName: String
)

data class OnlineStoreResponse(
    @SerialName("storeCode")
    val storeCode: String,
    @SerialName("storeName")
    val storeName: String,
    @SerialName("url")
    val url: String? = null
)

data class ReviewThumbnail(
    @SerialName("reviewId")
    val reviewId: Int,
    @SerialName("rating")
    val rating: Float,
    @SerialName("reviewContents")
    val reviewContents: String,
    @SerialName("regDate")
    val regDate: String // Will Change to Kotlinx-datetime
)

data class SimilarProduct(
    @SerialName("productId")
    val productId: String,
    @SerialName("image")
    val image: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("rating")
    val rating: Float,
    @SerialName("reviewCnt")
    val reviewCount: Int,
)