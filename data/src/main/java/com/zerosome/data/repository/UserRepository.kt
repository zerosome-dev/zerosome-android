package com.zerosome.data.repository

import android.util.Log
import com.zerosome.datasource.remote.dto.response.LoginResponse
import com.zerosome.datasource.remote.dto.response.TokenResponse
import com.zerosome.datasource.remote.service.AuthService
import com.zerosome.network.NetworkError
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UserRepository {

    fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>>

    fun signUp(socialToken: String, socialType: String, nickname: String, marketingAgreement: Boolean): Flow<NetworkResult<TokenResponse>>

    fun login(socialToken: String, socialType: String): Flow<NetworkResult<LoginResponse>>

    fun revoke()

}

internal class UserRepositoryImpl @Inject constructor(
    private val userService: AuthService,
): UserRepository {
    override fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> = safeCall { userService.validateNickname(nickname) }
    override fun signUp(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): Flow<NetworkResult<TokenResponse>> = userService.join(socialToken, socialType = socialType, nickname = nickname, marketingAgreement)

    override fun login(socialToken: String, socialType: String): Flow<NetworkResult<LoginResponse>> = userService.login(socialToken, socialType)

    override fun revoke() {
    }
}

internal class FakeUserRepositoryImpl @Inject constructor(
): UserRepository {
    override fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> = flow {
        emit(NetworkResult.Loading)
        delay(500)
        if (nickname == "중복닉네임") {
            emit(NetworkResult.Success(false))
        } else {
            emit(NetworkResult.Success(true))
        }
    }

    override fun signUp(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): Flow<NetworkResult<TokenResponse>> = flow {
        emit(NetworkResult.Loading)
        delay(500)
        Log.d("CPRI", "SOCIAL TOKEN : $socialToken, SOCIAL TYPE : $socialType, NICKNAME : $nickname, MARKETING : $marketingAgreement")
        if (socialToken.isEmpty() || nickname.isEmpty()) {
            emit(NetworkResult.Error(NetworkError.INVALID_PARAMETER))
        } else {
            emit(NetworkResult.Success(TokenResponse("FAKE ACCESS TOKEN", "FAKE REFRESH TOKEN")))
        }
    }

    override fun login(
        socialToken: String,
        socialType: String
    ): Flow<NetworkResult<LoginResponse>> = flow {
        emit(NetworkResult.Loading)
        delay(500)
        Log.d("CPRI", "SOCIAL TOKEN : $socialToken, SOCIAL TYPE : $socialType")
        if (socialToken.isEmpty()) {
            emit(NetworkResult.Error(NetworkError.INVALID_PARAMETER))
        } else {
            emit(NetworkResult.Success(LoginResponse(false, token = null)))
        }
    }

    override fun revoke() {
    }

}