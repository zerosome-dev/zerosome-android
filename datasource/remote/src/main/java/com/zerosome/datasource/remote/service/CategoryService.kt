package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.response.CategoryResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryService @Inject constructor(
    private val client: HttpClient
) : BaseService {
    override val apiRoute: String
        get() = "api/app/v1/category"

    fun getAllCategories(): Flow<NetworkResult<List<CategoryResponse>>> = safeCall {
        client.get(urlString = "$apiRoute/list").body()
    }
}