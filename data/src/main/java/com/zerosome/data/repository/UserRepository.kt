package com.zerosome.data.repository

import android.util.Log
import com.zerosome.data.apiCall
import com.zerosome.data.mapper.domainModel
import com.zerosome.datasource.local.source.TokenSource
import com.zerosome.datasource.remote.service.AuthService
import com.zerosome.datasource.remote.service.UserService
import com.zerosome.domain.model.UserBasicInfo
import com.zerosome.domain.repository.UserRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userService: AuthService,
    private val userDetailService: UserService,
    private val tokenSource: TokenSource,
) : UserRepository {
    override suspend fun validateNickname(nickname: String) = apiCall(
        block = { userService.validateNickname(nickname) },
        transform = { it == true }
    )

    override suspend fun signUp(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): Boolean = apiCall(
        block = { userService.join(socialToken, socialType, nickname, marketingAgreement) },
        transform = {
            it?.let { token ->
                tokenSource.updateToken(token.accessToken, token.refreshToken)
            } ?: false
        }
    )

    override suspend fun login(socialToken: String, socialType: String): Boolean = apiCall(
        block = { userService.login(socialToken, socialType) },
        transform = {
            it?.let { response ->
                response.token?.let { token ->
                    tokenSource.updateToken(token.accessToken, token.refreshToken)
                } ?: false
            } ?: throw com.zerosome.core.constants.ClientExceptions(com.zerosome.core.constants.ClientError.LOGIN_NOT_VALIDATED_EXCEPTION)
        }
    )

    override suspend fun checkUserLogin(): Boolean = coroutineScope {
        tokenSource.getTokenEntity() != null
    }

    override suspend fun deleteAccessToken(): Boolean = coroutineScope {
        tokenSource.updateToken(null, null)
    }

    override suspend fun getUserData(): UserBasicInfo = apiCall(
        block = { userDetailService.getUserDetailData() },
        transform = { it?.domainModel ?: throw com.zerosome.core.constants.ClientExceptions(com.zerosome.core.constants.ClientError.RESPONSE_NOT_VALIDATE_EXCEPTION) }
    )

    // 차후 탈퇴 관련 수정 예정
    override suspend fun revoke(): Boolean = apiCall(
        block = {
            val token = tokenSource.getTokenEntity() ?: throw com.zerosome.core.constants.ClientExceptions(
                com.zerosome.core.constants.ClientError.AUTH_NOT_VALIDATED
            )
            userService.revoke(token.accessToken)
        },
        transform = {
            deleteAccessToken()
        }
    )

}