package com.zerosome.data.mapper

import com.zerosome.datasource.remote.dto.response.BrandResponse
import com.zerosome.datasource.remote.dto.response.CafeResponse
import com.zerosome.datasource.remote.dto.response.HomeRolloutResponse
import com.zerosome.datasource.remote.dto.response.NutrientResponse
import com.zerosome.datasource.remote.dto.response.OfflineStoreResponse
import com.zerosome.datasource.remote.dto.response.OnlineStoreResponse
import com.zerosome.datasource.remote.dto.response.ProductDetailResponse
import com.zerosome.datasource.remote.dto.response.ProductResponse
import com.zerosome.datasource.remote.dto.response.ReviewThumbnailResponse
import com.zerosome.datasource.remote.dto.response.SimilarProductResponse
import com.zerosome.domain.model.Brand
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.CategoryProduct
import com.zerosome.domain.model.Nutrient
import com.zerosome.domain.model.Product
import com.zerosome.domain.model.RelatedProduct
import com.zerosome.domain.model.ReviewThumbnail
import com.zerosome.domain.model.Rollout
import com.zerosome.domain.model.Store

val BrandResponse.domainModel
    get() = Brand(brandCode, brandName)

val CafeResponse.domainModel
    get() = Cafe(id, image, categoryDepth1Id, categoryDepth2Id, name, brand, rating, reviewCount)

val HomeRolloutResponse.domainModel
    get() = Rollout(id, image, categoryDepth1, categoryDepth2, name, salesStore?.map { it ?: "" })

val ProductDetailResponse.domainModel
    get() = Product(
        productId = id,
        image = image,
        brandName = brandName,
        productName = productName,
        nutrientList = nutrientList.map { it.domainModel },
        onlineStoreList = onlineStoreList.map { it.domainModel },
        offlineStoreList = offlineStoreList.map { it.domainModel },
        rating = rating,
        reviewCount = reviewCount,
        reviewThumbnailList = reviewThumbnailList.map { it.domainModel },
        relatedProductList = similarProductList.map { it.domainModel })

val NutrientResponse.domainModel
    get() = Nutrient(
        nutrientName = nutrientName,
        percentage = servingPercent,
        amount = amount,
        serviceStandard = percentageUnit,
        amountStandard = amountStandard
    )

val OnlineStoreResponse.domainModel
    get() = Store.Online(url = url, storeCode = storeCode, storeName = storeName)

val OfflineStoreResponse.domainModel
    get() = Store.Offline(storeCode = storeCode, storeName = storeName)

val ReviewThumbnailResponse.domainModel
    get() = ReviewThumbnail(
        reviewId = reviewId,
        rating = rating,
        reviewContents = reviewContents,
        regDate = regDate
    )

val SimilarProductResponse.domainModel
    get() = RelatedProduct(
        productId = productId,
        image = image,
        productName = productName,
        rating = rating,
        reviewCount = reviewCount
    )

val ProductResponse.domainModel
    get() = CategoryProduct(
        id = productId,
        image = image,
        name = productName,
        brand = brandName,
        rating = rating,
        reviewCount = reviewCount
    )
