package com.zerosome.data.repository

import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.remote.service.HomeService
import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout
import com.zerosome.domain.repository.HomeRepository
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
) : HomeRepository {

    override fun getBanner(): Flow<NetworkResult<List<Banner>>> = safeCall { homeService.getBanners() }.mapToDomain { it.map { data -> data.domainModel } }

    override fun getRollout(): Flow<NetworkResult<List<Rollout>>> {
        return safeCall { homeService.getRollout() }.mapToDomain { it.map { data -> data.domainModel } }
    }

    override fun getCafe(): Flow<NetworkResult<List<Cafe>>> =
        safeCall { homeService.getCafe() }.mapToDomain { it.map { data -> data.domainModel } }

}