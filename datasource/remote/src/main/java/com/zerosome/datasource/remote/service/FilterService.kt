package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.response.BrandResponse
import com.zerosome.datasource.remote.dto.response.CategoryDepth2Response
import com.zerosome.datasource.remote.dto.response.ZeroCategoryResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterService @Inject constructor(
    private val apiClient: HttpClient
): BaseService {
    override val apiRoute: String
        get() = "api/app/v1/filter"

    suspend fun getDepth2CategoryById(id: String): BaseResponse<List<CategoryDepth2Response>> = apiClient.get("$apiRoute/sub-category/$id").body()


    suspend fun getBrandFilter(): BaseResponse<List<BrandResponse>> = apiClient.get("$apiRoute/brand").body()


    suspend fun getZeroCategoryFilter(): BaseResponse<List<ZeroCategoryResponse>> = apiClient.get("$apiRoute/zero-category").body()
}