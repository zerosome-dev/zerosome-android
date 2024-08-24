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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userService: AuthService,
    private val userDetailService: UserService,
    private val tokenSource: TokenSource,
) : UserRepository {
    private val currentAccessToken = tokenSource.getAccessToken().stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        CoroutineScope(Dispatchers.IO)
            .launch {
                currentAccessToken.collect()
            }
    }

    override fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> =
        userService.validateNickname(nickname)

    override fun signUp(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreement: Boolean
    ): Flow<NetworkResult<Unit>> = userService.join(
        socialToken,
        socialType = socialType,
        nickname = nickname,
        marketingAgreement
    ).flatMapMerge {
        when (it) {
            NetworkResult.Loading -> flowOf(NetworkResult.Loading)
            is NetworkResult.Success -> {
                tokenSource.updateToken(it.data.accessToken, it.data.refreshToken)
                flowOf(NetworkResult.Success(Unit))
            }

            is NetworkResult.Error -> flowOf(NetworkResult.Error(it.error))
        }
    }

    override fun login(socialToken: String, socialType: String): Flow<NetworkResult<Boolean>> {
        return userService.login(socialToken, socialType).map {
            when (it) {
                NetworkResult.Loading -> NetworkResult.Loading
                is NetworkResult.Success -> {
                    tokenSource.updateToken(it.data.token?.accessToken, it.data.token?.refreshToken)
                    NetworkResult.Success(it.data.isMember)
                }

                is NetworkResult.Error -> NetworkResult.Error(it.error)
            }
        }
    }


    override fun checkUserLogin(): Flow<Boolean> =
        currentAccessToken.onEach { Log.d("CPRI", "TOKEN ENTITY : $it") }
            .map { it?.accessToken.isNullOrEmpty().not() }

    override fun deleteAccessToken(): Flow<Boolean> = flow {
        tokenSource.updateToken(null, null)
        emit(true)
    }.catch {
        emit(false)
    }

    override fun getUserData(): Flow<NetworkResult<UserBasicInfo>> = safeCall {
        userDetailService.getUserDetailData()
    }.mapToDomain { it.domainModel }

    override fun revoke(): Flow<NetworkResult<Boolean>> =
        tokenSource.getAccessToken().map { it?.accessToken }.flatMapLatest {
            userService.revoke(
                requireNotNull(it)
            )
        }
            .map {
                when (it) {
                    NetworkResult.Loading -> NetworkResult.Loading
                    is NetworkResult.Success -> NetworkResult.Success(true)
                    is NetworkResult.Error -> NetworkResult.Error(it.error)

                }
            }.catch {
                emit(NetworkResult.Error(NetworkError.UNKNOWN))
            }
}