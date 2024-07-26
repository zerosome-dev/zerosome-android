package com.zerosome.data.mapper

import com.zerosome.datasource.remote.dto.response.HomeBannerResponse
import com.zerosome.domain.model.Banner

val HomeBannerResponse.domainModel
    get() = Banner(id, image = image, url = url ?: "")