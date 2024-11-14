package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.request.JoinRequest
import com.zerosome.datasource.remote.dto.response.LoginResponse
import com.zerosome.datasource.remote.dto.response.TokenResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AuthService @Inject constructor(
    private val client: HttpClient
) : BaseService {

    override val apiRoute: String = "api/v1/auth"

    suspend fun login(
        socialToken: String,
        socialType: String
    ): BaseResponse<LoginResponse> = client.post(urlString = apiRoute) {
        header("Authorization", "Bearer $socialToken")
        parameter("socialType", socialType)
    }.body()

    suspend fun validateNickname(nickname: String): BaseResponse<Boolean> =
        client.get("$apiRoute/nickname") {
            parameter("nickname", nickname)
        }.body()


    suspend fun join(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): BaseResponse<TokenResponse> =
        client.post(urlString = "$apiRoute/join") {
            header("Authorization", "Bearer $socialToken")
            parameter("socialType", socialType)
            setBody(JoinRequest(nickname, marketingAgreement))
        }.body()

    suspend fun revoke(token: String): BaseResponse<Unit> = client.delete(urlString = "$apiRoute/logout") {}.body()


    suspend fun logout(token: String): BaseResponse<Unit> = client.post(apiRoute).body()

}