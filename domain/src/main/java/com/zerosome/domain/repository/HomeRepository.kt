package com.zerosome.domain.repository

import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout

interface HomeRepository {
    suspend fun getBanner(): List<Banner>

    suspend fun getRollout(): List<Rollout>

    suspend fun getCafe(): List<Cafe>
}
