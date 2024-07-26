package com.zerosome.data.repository

import android.util.Log
import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.remote.service.HomeService
import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout
import com.zerosome.domain.repository.HomeRepository
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    private val tokenRepository: TokenRepository,
) : HomeRepository {
    private val currentAccessToken = tokenRepository.getToken().stateIn(
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

    override fun getBanner(): Flow<NetworkResult<List<Banner>>> = safeCall {
        homeService.getBanners(currentAccessToken.value?.accessToken)
    }.mapToDomain { it.map { data -> data.domainModel } }

    override fun getRollout(): Flow<NetworkResult<List<Rollout>>> {
        Log.d("CPRI", "LOG STARTED")
        return safeCall {
            homeService.getRollout(currentAccessToken.value?.accessToken).also {
                Log.d("CPRI", "LOGGER")
            }
        }.mapToDomain { it.map { data -> data.domainModel } }
    }

    override fun getCafe(): Flow<NetworkResult<List<Cafe>>> = safeCall {
        homeService.getCafe(currentAccessToken.value?.accessToken)
    }.mapToDomain { it.map { data -> data.domainModel } }

}