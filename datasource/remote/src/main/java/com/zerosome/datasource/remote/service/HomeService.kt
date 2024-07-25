package com.zerosome.datasource.remote.service

import android.util.Log
import com.zerosome.datasource.remote.dto.response.CafeResponse
import com.zerosome.datasource.remote.dto.response.HomeBannerResponse
import com.zerosome.datasource.remote.dto.response.HomeRolloutResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.headers
import javax.inject.Inject

class HomeService @Inject constructor(
    private val httpClient: HttpClient
) : BaseService {
    override val apiRoute: String = "api/app/v1/home"
    suspend fun getBanners(token: String? = null): BaseResponse<List<HomeBannerResponse>> = httpClient.get(urlString = "$apiRoute/banners") {
        token?.let {
            headers {
                append("Authorization", "Bearer $it")
            }
        }
    }.body()

    suspend fun getRollout(token: String? = null): BaseResponse<List<HomeRolloutResponse>> =
        httpClient.get(urlString = "$apiRoute/rollout") {
            token?.let {
                header("Authorization", "Bearer $it")
            }
        }.body()

    suspend fun getCafe(token: String? = null): BaseResponse<List<CafeResponse>> {
        Log.d("CPRI", "BODY")
        return httpClient.get(urlString = "$apiRoute/cafe") {
            token?.let {
                headers {
                    append("Authorization", "Bearer $it")
                }
            }
        }.body()
    }

}