package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.response.ReviewCreateRequest
import com.zerosome.datasource.remote.dto.response.ReviewDetailResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeGet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewService @Inject constructor(
    private val apiClient: HttpClient
) : BaseService{
    override val apiRoute: String
        get() = "api/app/v1/review"

    fun getReviews(
        token: String? = null,
        productId: String
    ) = apiClient.safeGet<Flow<NetworkResult<List<ReviewDetailResponse>>>>(token, "$apiRoute/$productId")

    suspend fun createReview(
        productId: Int,
        rating: Float,
        contents: String? = null
    ) = apiClient.post(apiRoute) {
        contentType(ContentType.Application.Json)
        setBody(
            ReviewCreateRequest(
                productId,
                rating,
                contents
            ),
        )
    }.body<BaseResponse<Unit>>()
}