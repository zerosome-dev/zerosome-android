package com.zerosome.data.mapper

import com.zerosome.datasource.remote.dto.response.CategoryDepth2Response
import com.zerosome.datasource.remote.dto.response.CategoryResponse
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2

val CategoryResponse.domainModel
    get() = CategoryDepth1(
        categoryCode = d1CategoryCode,
        categoryName = d1CategoryName,
        categoryDepth2 = d2Category.map { it.domainModel }
    )

val CategoryDepth2Response.domainModel
    get() = CategoryDepth2(
        categoryCode = categoryCode,
        categoryName = categoryName,
        categoryImage = categoryImage,
        optional = optional == true
    )
