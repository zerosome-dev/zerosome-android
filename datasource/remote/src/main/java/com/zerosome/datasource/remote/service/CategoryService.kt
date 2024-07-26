package com.zerosome.datasource.remote.service

import com.zerosome.network.BaseService
import io.ktor.client.HttpClient
import javax.inject.Inject

class CategoryService @Inject constructor(
    private val client: HttpClient
) : BaseService {
    override val apiRoute: String
        get() = "api/app/v1/category"

//    fun getAllCategories(token: String? = null): Flow<CategoryResponse>> =
//        client.safeGet(token, "$apiRoute/list")

}