package com.zerosome.data.repository

import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.remote.service.FilterService
import com.zerosome.domain.model.Brand
import com.zerosome.domain.model.ZeroCategory
import com.zerosome.domain.repository.FilterRepository
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val filterService: FilterService
): FilterRepository{
    override fun getBrands(): Flow<NetworkResult<List<Brand>>> = safeCall {
        filterService.getBrandFilter()
    }.mapToDomain { it.map { item -> item.domainModel } }

    override fun getZeroTag(): Flow<NetworkResult<List<ZeroCategory>>> = safeCall{
        filterService.getZeroCategoryFilter()
    }.mapToDomain { it.map { item -> item.domainModel } }
}