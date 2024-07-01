package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.response.BrandResponse
import com.zerosome.datasource.remote.dto.response.CategoryDepth2Response
import com.zerosome.datasource.remote.dto.response.ZeroCategoryResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeGet
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterService @Inject constructor(
    private val apiClient: HttpClient
): BaseService {
    override val apiRoute: String
        get() = "api/app/v1/filter"

    fun getDepth2CategoryById(token: String? = null, id: String): Flow<NetworkResult<List<CategoryDepth2Response>>> = apiClient.safeGet(token, "$apiRoute/$id")

    fun getBrandFilter(token: String? = null): Flow<NetworkResult<List<BrandResponse>>> = apiClient.safeGet(token, "$apiRoute/brand")

    fun getZeroCategoryFilter(token: String? = null): Flow<NetworkResult<List<ZeroCategoryResponse>>> = apiClient.safeGet(token, "$apiRoute/zero-category")

}