package com.zerosome.data.repository

import com.zerosome.data.apiCall
import com.zerosome.data.mapper.domainModel
import com.zerosome.datasource.remote.service.HomeService
import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout
import com.zerosome.domain.repository.HomeRepository
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
) : HomeRepository {

    override suspend fun getBanner(): List<Banner> = apiCall(
        block = { homeService.getBanners()},
        transform = { it?.map { data -> data.domainModel } ?: emptyList() }
    )

    override suspend fun getRollout(): List<Rollout> = apiCall(
        block = { homeService.getRollout() },
        transform = { it?.map { data -> data.domainModel } ?: emptyList() }
    )

    override suspend fun getCafe(): List<Cafe> = apiCall(
        block = { homeService.getCafe() },
        transform = { it?.map { data -> data.domainModel } ?: emptyList() }
    )
}