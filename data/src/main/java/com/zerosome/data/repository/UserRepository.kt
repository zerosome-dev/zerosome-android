package com.zerosome.data.repository

import com.zerosome.datasource.local.source.TokenSource
import com.zerosome.datasource.remote.dto.response.LoginResponse
import com.zerosome.datasource.remote.dto.response.TokenResponse
import com.zerosome.datasource.remote.service.AuthService
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface UserRepository {

    fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>>

    fun signUp(socialToken: String, socialType: String, nickname: String, marketingAgreement: Boolean): Flow<NetworkResult<TokenResponse>>

    fun login(socialToken: String, socialType: String): Flow<NetworkResult<LoginResponse>>

    fun revoke()

}

internal class UserRepositoryImpl @Inject constructor(
    private val userService: AuthService,
    private val source: TokenSource
): UserRepository {
    override fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> = userService.validateNickname(nickname)
    override fun signUp(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): Flow<NetworkResult<TokenResponse>> = userService.join(socialToken, socialType = socialType, nickname = nickname, marketingAgreement)

    override fun login(socialToken: String, socialType: String): Flow<NetworkResult<LoginResponse>> = userService.login(socialToken, socialType)

    override fun revoke() {
        TODO("Not yet implemented")
    }
}