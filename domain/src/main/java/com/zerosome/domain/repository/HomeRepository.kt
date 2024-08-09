package com.zerosome.domain.repository

import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getBanner(): Flow<NetworkResult<List<Banner>>>

    fun getRollout(): Flow<NetworkResult<List<Rollout>>>

    fun getCafe(): Flow<NetworkResult<List<Cafe>>>
}
