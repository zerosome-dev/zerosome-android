package com.zerosome.data.mapper

import com.zerosome.datasource.remote.dto.response.BrandResponse
import com.zerosome.datasource.remote.dto.response.CafeResponse
import com.zerosome.datasource.remote.dto.response.HomeRolloutResponse
import com.zerosome.domain.model.Brand
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout

val BrandResponse.domainModel
    get() = Brand(brandCode, brandName)

val CafeResponse.domainModel
    get() = Cafe(id, image, categoryDepth1Id, categoryDepth2Id, name, brand, rating, reviewCount)

val HomeRolloutResponse.domainModel
    get() = Rollout(id, image, categoryDepth1, categoryDepth2, name, salesStore)

