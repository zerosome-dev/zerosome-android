package com.zerosome.domain.repository

import com.zerosome.domain.model.Brand
import com.zerosome.domain.model.ZeroCategory
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getBrands(): Flow<NetworkResult<List<Brand>>>

    fun getZeroTag(): Flow<NetworkResult<List<ZeroCategory>>>


}