package com.zerosome.data.service

import com.zerosome.data.dto.response.CategoryDepth2Response
import com.zerosome.data.dto.response.CategoryProductResponse
import com.zerosome.data.dto.response.CategoryResponse
import com.zerosome.data.dto.response.PagedResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeGet
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryService @Inject constructor(
    private val client: HttpClient
) : BaseService {
    override val apiRoute: String
        get() = "api/app/v1/category"

    fun getAllCategories(token: String? = null): Flow<NetworkResult<List<CategoryResponse>>> =
        client.safeGet(token, "$apiRoute/list")

}