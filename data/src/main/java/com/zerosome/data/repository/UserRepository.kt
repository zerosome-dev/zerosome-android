package com.zerosome.data.repository

import android.util.Log
import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.local.source.TokenSource
import com.zerosome.datasource.remote.service.AuthService
import com.zerosome.datasource.remote.service.UserService
import com.zerosome.domain.model.UserBasicInfo
import com.zerosome.domain.repository.UserRepository
import com.zerosome.network.NetworkError
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userService: AuthService,
    private val userDetailService: UserService,
    private val tokenSource: TokenSource,
) : UserRepository {
    override fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> =
        userService.validateNickname(nickname)

    override fun signUp(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): Flow<NetworkResult<Boolean>> = userService.join(
        socialToken,
        socialType = socialType,
        nickname = nickname,
        marketingAgreement
    ).map {
        when (it) {
            NetworkResult.Loading -> NetworkResult.Loading
            is NetworkResult.Success -> {
                if (tokenSource.updateToken(it.data.accessToken, it.data.refreshToken)) {
                    NetworkResult.Success(true)
                } else {
                    NetworkResult.Error(NetworkError.UNAUTHORIZED)
                }
            }

            is NetworkResult.Error -> NetworkResult.Error(it.error)
        }
    }

    override fun login(socialToken: String, socialType: String): Flow<NetworkResult<Boolean>> {
        return userService.login(socialToken, socialType).map {
            when (it) {
                NetworkResult.Loading -> NetworkResult.Loading
                is NetworkResult.Success -> {
                    if (tokenSource.updateToken(
                            it.data.token?.accessToken,
                            it.data.token?.refreshToken
                        )
                    ) {
                        NetworkResult.Success(it.data.isMember)
                    } else {
                        NetworkResult.Error(NetworkError.UNAUTHORIZED)
                    }

                }

                is NetworkResult.Error -> NetworkResult.Error(it.error)
            }
        }
    }


    override fun checkUserLogin(): Flow<Boolean> = flow {
        val token = tokenSource.getTokenEntity()
        emit(token?.accessToken.isNullOrEmpty().not())
    }

    override fun deleteAccessToken(): Flow<Boolean> = flow {
        emit(tokenSource.updateToken(null, null))
    }.catch {
        emit(false)
    }

    override fun getUserData(): Flow<NetworkResult<UserBasicInfo>> = safeCall {
        userDetailService.getUserDetailData()
    }.mapToDomain { it.domainModel }


    override fun revoke(): Flow<NetworkResult<Boolean>> {
        val token = runBlocking { tokenSource.getTokenEntity() }
        if (token?.accessToken.isNullOrEmpty()) return flowOf(NetworkResult.Error(NetworkError.UNAUTHORIZED))
        return userService.revoke(token?.accessToken ?: "")
    }

    override fun logout(): Flow<NetworkResult<Boolean>> {
        val token = runBlocking { tokenSource.getTokenEntity() }
        if (token?.accessToken.isNullOrEmpty()) return flowOf(NetworkResult.Error(NetworkError.UNAUTHORIZED))
        return userService.logout(token?.accessToken ?: "")
    }
}