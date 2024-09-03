package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.request.CategoryProductRequest
import com.zerosome.datasource.remote.dto.response.PagedResponse
import com.zerosome.datasource.remote.dto.response.ProductDetailResponse
import com.zerosome.datasource.remote.dto.response.ProductResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ProductService @Inject constructor(
    private val apiClient: HttpClient
) : BaseService {
    override val apiRoute: String
        get() = "api/app/v1/product"

    suspend fun getProductByCategory(
        d2CategoryCode: String,
        orderType: String,
        brandList: List<String>, // Next, will change to brandList,
        zeroCategoryList: List<String>, // Next, will Change to ZeroCtg
        cursor: Int? = null,
        limit: Int? = null
    ) = apiClient.post(urlString = "$apiRoute/category/$d2CategoryCode") {
        cursor?.let {
            parameter("offset", it)
        }
        limit?.let {
            parameter("limit", it)
        }
        contentType(ContentType.Application.Json)
        setBody(
            CategoryProductRequest(
                orderType,
                brandList,
                zeroCategoryList
            )
        )
    }.body<BaseResponse<PagedResponse<ProductResponse>>>()

    suspend fun getProductDetail(
        productId: Int
    ): BaseResponse<ProductDetailResponse> =
        apiClient.get(urlString = "$apiRoute/detail/$productId").body()
}