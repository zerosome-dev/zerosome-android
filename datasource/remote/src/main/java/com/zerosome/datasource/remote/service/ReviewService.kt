package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.response.ReviewCreateRequest
import com.zerosome.datasource.remote.dto.response.ReviewDetailResponse
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

class ReviewService @Inject constructor(
    private val apiClient: HttpClient
) : BaseService{
    override val apiRoute: String
        get() = "api/app/v1/review"

    suspend fun getReviews(
        productId: Int,
        cursorId: Int?
    ) = apiClient.get("$apiRoute/$productId") {
        parameter("cursor", cursorId)
        parameter("limit", 10)
        contentType()
    }.body<BaseResponse<List<ReviewDetailResponse>>>()

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