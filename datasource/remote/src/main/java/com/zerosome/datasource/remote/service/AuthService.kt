package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.request.JoinRequest
import com.zerosome.datasource.remote.dto.response.LoginResponse
import com.zerosome.datasource.remote.dto.response.TokenResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import com.zerosome.network.safePost
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthService @Inject constructor(
    private val client: HttpClient
) : BaseService {

    override val apiRoute: String = "api/v1/auth"

    fun login(
        socialToken: String,
        socialType: String
    ): Flow<NetworkResult<LoginResponse>> = safeCall {
        client.post(urlString = apiRoute) {
            header("Authorization", "Bearer $socialToken")
            parameter("socialType", socialType)
        }.body()
    }

    fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> = safeCall {
        client.get("$apiRoute/nickname") {
            parameter("nickname", nickname)
        }.body()
    }


    fun join(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): Flow<NetworkResult<TokenResponse>> = safeCall {
        client.post(urlString = "$apiRoute/join") {
            header("Authorization", "Bearer $socialToken")
            parameter("socialType", socialType)
            setBody(JoinRequest(nickname, marketingAgreement))
        }.body()
    }

    fun revoke(token: String): Flow<NetworkResult<Unit>> = safeCall {
        client.delete(urlString = "$apiRoute/logout") {}.body()
    }

    fun logout(token: String): Flow<NetworkResult<Unit>> = client.safePost(token, apiRoute)

}