package com.zerosome.data.service

import com.zerosome.data.dto.request.JoinRequest
import com.zerosome.data.dto.response.LoginResponse
import com.zerosome.data.dto.response.TokenResponse
import com.zerosome.network.BaseService
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeDelete
import com.zerosome.network.safeGet
import com.zerosome.network.safePost
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AuthService @Inject constructor(
    private val client: HttpClient): BaseService {

    override val apiRoute: String = "api/v1/auth"

    fun login(socialType: String): Flow<NetworkResult<LoginResponse>> = client.safePost(url = apiRoute) {
        parameter("socialType", socialType)
    }

    fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> = client.safeGet(url = "$apiRoute/nickname") {
        parameter("nickname", nickname)
    }

    fun join(socialType: String, nickname: String, marketingAgreement: Boolean): Flow<NetworkResult<TokenResponse>> = client.safePost(url = "$apiRoute/join") {
        parameter("socialType", socialType)
        setBody(JoinRequest(nickname, marketingAgreement))
    }

    fun revoke(token: String): Flow<NetworkResult<Unit>> = client.safeDelete(token, "$apiRoute/logout")

    fun logout(token: String): Flow<NetworkResult<Unit>> = client.safePost(token, apiRoute)

}