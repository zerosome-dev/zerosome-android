package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.response.CafeResponse
import com.zerosome.datasource.remote.dto.response.HomeBannerResponse
import com.zerosome.datasource.remote.dto.response.HomeRolloutResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class HomeService @Inject constructor(
    private val httpClient: HttpClient
) : BaseService {
    override val apiRoute: String = "api/app/v1/home"
    suspend fun getBanners(): BaseResponse<List<HomeBannerResponse>> = httpClient.get(urlString = "$apiRoute/banners").body()

    suspend fun getRollout(): BaseResponse<List<HomeRolloutResponse>> = httpClient.get(urlString = "$apiRoute/rollout").body()

    suspend fun getCafe(): BaseResponse<List<CafeResponse>> = httpClient.get(urlString = "$apiRoute/cafe").body()

}