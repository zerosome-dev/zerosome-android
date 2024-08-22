package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.request.CategoryProductRequest
import com.zerosome.datasource.remote.dto.response.PagedResponse
import com.zerosome.datasource.remote.dto.response.ProductDetailResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import com.zerosome.network.safeGet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import javax.inject.Inject

class ProductService @Inject constructor(
    private val apiClient: HttpClient
): BaseService {
    override val apiRoute: String
        get() = "api/app/v1/product"

    fun getProductByCategory(
        token: String? = null,
        d2CategoryCode: String,
        orderType: String,
        brandList: List<String>, // Next, will change to brandList,
        zeroCategoryList: List<String>, // Next, will Change to ZeroCtg
        cursor: Int? = null,
        limit: Int? = null
    ) = apiClient.safeGet<PagedResponse<CategoryProductRequest>>(token, url = "$apiRoute/category/$d2CategoryCode") {
        cursor?.let {
            parameter("cursor", it)
        }
        limit?.let {
            parameter("limit", it)
        }
        setBody(
            CategoryProductRequest(
                d2CategoryCode,
                orderType,
                brandList,
                zeroCategoryList
            )
        )
    }

    suspend fun getProductDetail(
        productId: Int
    ): BaseResponse<ProductDetailResponse> =
        apiClient.get(urlString = "$apiRoute/detail/$productId").body()
}