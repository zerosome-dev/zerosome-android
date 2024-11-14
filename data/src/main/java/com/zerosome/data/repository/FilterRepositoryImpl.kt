package com.zerosome.data.repository

import com.zerosome.data.apiCall
import com.zerosome.data.mapper.domainModel
import com.zerosome.datasource.remote.service.FilterService
import com.zerosome.domain.model.Brand
import com.zerosome.domain.model.ZeroCategory
import com.zerosome.domain.repository.FilterRepository
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val filterService: FilterService
): FilterRepository{
    override suspend fun getBrands(): List<Brand> = apiCall(
        block = {
            filterService.getBrandFilter()
        },
        transform = {
            it?.map { data -> data.domainModel } ?: throw com.zerosome.core.constants.ClientExceptions(
                com.zerosome.core.constants.ClientError.RESPONSE_NOT_VALIDATE_EXCEPTION
            )
        }
    )

    override suspend fun getZeroTag(): List<ZeroCategory> = apiCall(
        block = {
            filterService.getZeroCategoryFilter()
        },
        transform = {
            it?.map { data -> data.domainModel } ?: throw com.zerosome.core.constants.ClientExceptions(
                com.zerosome.core.constants.ClientError.RESPONSE_NOT_VALIDATE_EXCEPTION
            )
        }
    )
}