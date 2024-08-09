package com.zerosome.domain.repository

import com.zerosome.domain.model.Product
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductDetail(id: String): Flow<NetworkResult<Product>>
}