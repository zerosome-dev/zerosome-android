package com.zerosome.main.home

import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.Rollout

internal sealed interface HomeAction: UIAction {
    data class ClickBanner(val banner: Banner): HomeAction

    data class ClickRollout(val rollout: Rollout): HomeAction

    data object ClickRolloutMore: HomeAction

    data class ClickCafe(val cafeResponse: Cafe): HomeAction

    data object ClickCafeMore: HomeAction
}

internal sealed interface HomeIntent: UIIntent {
    data object Initialize: HomeIntent

    data class MoveToBanner(val banner: Banner): HomeIntent

    data class MoveToDetail(val productId: Int): HomeIntent

    data object MoveToRollout: HomeIntent

    data object MoveToCategoryDetail: HomeIntent
}

internal data class HomeState(
    val banners: List<Banner> = emptyList(),
    val rolloutList: List<Rollout> = emptyList(),
    val cafeList: List<Cafe> = emptyList()
)

internal sealed interface HomeEffect: UIEffect {

}

class HomeViewModel {
}