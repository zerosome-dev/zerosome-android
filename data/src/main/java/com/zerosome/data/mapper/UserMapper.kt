package com.zerosome.data.mapper

import com.zerosome.datasource.remote.dto.response.UserResponse
import com.zerosome.domain.model.UserBasicInfo

val UserResponse.domainModel
    get() = UserBasicInfo(
        nickname = nickname,
        reviewCount = reviewCnt
    )