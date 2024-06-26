package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.request.JoinRequest
import com.zerosome.datasource.remote.dto.response.LoginResponse
import com.zerosome.datasource.remote.dto.response.TokenResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeDelete
import com.zerosome.network.safeGet
import com.zerosome.network.safePost
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
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
    ): Flow<NetworkResult<LoginResponse>> = client.safePost(url = apiRoute) {
        header("Authorization", socialToken)
        parameter("socialType", socialType)
    }

    fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> =
        client.safeGet(url = "$apiRoute/nickname") {
            parameter("nickname", nickname)
        }

    fun join(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): Flow<NetworkResult<TokenResponse>> = client.safePost(url = "$apiRoute/join") {
        header("Authorization", socialToken)
        parameter("socialType", socialType)
        setBody(JoinRequest(nickname, marketingAgreement))
    }

    fun revoke(token: String): Flow<NetworkResult<Unit>> =
        client.safeDelete(token, "$apiRoute/logout")

    fun logout(token: String): Flow<NetworkResult<Unit>> = client.safePost(token, apiRoute)

}