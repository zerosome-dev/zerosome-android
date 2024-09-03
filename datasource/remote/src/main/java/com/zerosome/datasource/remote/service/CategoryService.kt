package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.response.CategoryResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class CategoryService @Inject constructor(
    private val client: HttpClient
) : BaseService {
    override val apiRoute: String
        get() = "api/app/v1/category"

    suspend fun getAllCategories(): BaseResponse<List<CategoryResponse>> = client.get(urlString = "$apiRoute/list").body()

}