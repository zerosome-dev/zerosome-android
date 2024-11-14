package com.zerosome.domain.repository

import com.zerosome.domain.model.CategoryProduct
import com.zerosome.domain.model.Page
import com.zerosome.domain.model.Product
import com.zerosome.domain.model.SortItem

interface ProductRepository {
    suspend fun getProductDetail(id: Int): Product

    suspend fun getProductsByCategory(
        categoryCode: String,
        offset: Int?,
        limit: Int?,
        orderType: SortItem,
        brandList: List<String>,
        zeroTagList: List<String>
    ): Page<CategoryProduct>
}