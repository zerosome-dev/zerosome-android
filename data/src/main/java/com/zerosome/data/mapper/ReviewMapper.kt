package com.zerosome.data.mapper

import com.zerosome.datasource.remote.dto.response.ReviewDetailResponse
import com.zerosome.domain.model.Review

val ReviewDetailResponse.domainModel
    get() = Review(
        reviewId = reviewId,
        rating = rating,
        reviewContents = reviewContents ?: "",
        regDate = regDate,
        authorNickname = nickname
    )