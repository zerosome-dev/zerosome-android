package com.zerosome.data.service

import com.zerosome.data.dto.response.CafeResponse
import com.zerosome.data.dto.response.HomeBannerResponse
import com.zerosome.data.dto.response.HomeRolloutResponse
import com.zerosome.data.dto.response.ProductDetailResponse
import com.zerosome.data.dto.response.ProductResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeGet
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeService @Inject constructor(
    private val httpClient: HttpClient
): BaseService{
    override val apiRoute: String = "api/app/v1/home"
    fun getBanners(token: String? = null): Flow<NetworkResult<List<HomeBannerResponse>>> = httpClient.safeGet(token, "$apiRoute/banners")

    fun getRollout(token: String? = null): Flow<NetworkResult<List<HomeRolloutResponse>>> = httpClient.safeGet(token, "$apiRoute/rollout")

    fun getCafe(token: String? = null): Flow<NetworkResult<List<CafeResponse>>> = httpClient.safeGet(token, "$apiRoute/cafe")
}