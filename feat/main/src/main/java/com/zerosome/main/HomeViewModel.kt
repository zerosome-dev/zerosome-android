package com.zerosome.main

import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Rollout

internal sealed interface HomeAction: UIAction {
    data class ClickBanner(val banner: Banner): HomeAction

    data class ClickRollout(val rollout: Rollout): HomeAction

    data object ClickRolloutMore: HomeAction

    data class ClickProduct(val productId: Int): HomeAction

    data object ClickCafeMore: HomeAction
}

internal sealed interface HomeIntent: UIIntent {

}

internal data class HomeState(
    val state: Int = 0
): UIState

internal sealed class HomeEffect: UIEffect

internal class HomeViewModel: BaseViewModel<HomeAction, HomeIntent, HomeState, HomeEffect>(
    initialState = HomeState()
){
    override fun actionPredicate(action: HomeAction): HomeIntent {
        TODO("Not yet implemented")
    }

    override fun collectIntent(intent: HomeIntent) {
        TODO("Not yet implemented")
    }
}