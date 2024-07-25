package com.zerosome.datasource.remote.service

//import com.zerosome.datasource.remote.dto.response.ReviewDetailResponse
//import com.zerosome.network.BaseService
//import com.zerosome.network.NetworkResult
//import com.zerosome.network.safeGet
//import com.zerosome.network.safePost
//import io.ktor.client.HttpClient
//import io.ktor.client.request.setBody
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class ReviewService @Inject constructor(
//    private val apiClient: HttpClient
//) : BaseService{
//    override val apiRoute: String
//        get() = "api/app/v1/review"
//
//    fun getReviews(
//        token: String? = null,
//        productId: String
//    ) = apiClient.safeGet<Flow<NetworkResult<List<ReviewDetailResponse>>>>(token, "$apiRoute/$productId")
//
//    fun createReview(
//        token: String? = null,
//        productId: Int,
//        rating: Float,
//        contents: String? = null
//    ) = apiClient.safePost<Flow<NetworkResult<Unit>>>(token, apiRoute) {
//        setBody(
//            ReviewCreateRequest(
//                productId,
//                rating,
//                contents
//            )
//        )
//    }
//}