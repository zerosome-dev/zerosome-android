package com.zerosome.main.home

import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout

sealed interface HomeUiModel {
    data class Banners(val bannerList: List<Banner>): HomeUiModel

    data class Rollouts(val rollout: List<Rollout>): HomeUiModel

    data class Cafes(val cafe: List<Cafe>): HomeUiModel
}