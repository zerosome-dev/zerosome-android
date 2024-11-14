package com.zerosome.domain.repository

import com.zerosome.domain.model.Brand
import com.zerosome.domain.model.ZeroCategory

interface FilterRepository {
    suspend fun getBrands(): List<Brand>

    suspend fun getZeroTag(): List<ZeroCategory>


}