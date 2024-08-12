package com.zerosome.main.home

import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout

sealed class HomeUiModel(val keyId: Int) {
    data class Banners(val bannerList: List<Banner>): HomeUiModel(1)

    data class Rollouts(val rollout: List<Rollout>): HomeUiModel(2)

    data class Cafes(val cafe: List<Cafe>): HomeUiModel(3)
}