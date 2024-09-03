package com.zerosome.domain.repository

import com.zerosome.domain.model.CategoryProduct
import com.zerosome.domain.model.Page
import com.zerosome.domain.model.Product
import com.zerosome.domain.model.SortItem
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductDetail(id: Int): Flow<NetworkResult<Product>>

    fun getProductsByCategory(
        categoryCode: String,
        offset: Int?,
        limit: Int?,
        orderType: SortItem,
        brandList: List<String>,
        zeroTagList: List<String>
    ): Flow<NetworkResult<Page<CategoryProduct>>>
}