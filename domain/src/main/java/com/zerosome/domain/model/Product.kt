package com.zerosome.domain.model

data class Product(
    val productId: Int,
    val image: String,
    val brandName: String,
    val productName: String,
    val nutrientList: List<Nutrient>,
    val onlineStoreList: List<Store.Online>,
    val offlineStoreList: List<Store.Offline>,
    val rating: Float,
    val reviewCount: Int,
    val reviewThumbnailList: List<ReviewThumbnail>,
    val relatedProductList: List<RelatedProduct>
)

data class CategoryProduct(
    val id: Int,
    val image: String,
    val categoryDepth1Id: Int,
    val categoryDepth2id: Int,
    val name: String,
    val brand: String,
    val rating: Float,
    val reviewCount: Int,
)

data class Nutrient(
    val nutrientName: String,
    val servingPercent: Int,
    val amount: Int,
    val serviceStandard: String,
    val amountStandard: String
)

sealed class Store(
    open val storeCode: String,
    open val storeName: String,
) {
    data class Online(
        val url: String?,
        override val storeCode: String,
        override val storeName: String
    ) : Store(storeCode, storeName)

    data class Offline(override val storeCode: String, override val storeName: String) :
        Store(storeCode, storeName)
}

data class ReviewThumbnail(
    val reviewId: Int,
    val rating: Float,
    val reviewContents: String,
    val regDate: String
)

data class RelatedProduct(
    val productId: String,
    val image: String,
    val productName: String,
    val rating: Float,
    val reviewCount: Int
)
