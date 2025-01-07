package com.zerosome.main.home

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.zerosome.core.analytics.LogName
import com.zerosome.core.analytics.LogProperty
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.model.Rollout
import com.zerosome.feat.core.BaseViewModel
import com.zerosome.feat.core.UIAction
import com.zerosome.feat.core.UIEffect
import com.zerosome.feat.core.UIIntent
import com.zerosome.feat.core.UIState
import com.zerosome.product.GetCafeMenuUseCase
import com.zerosome.product.GetRolloutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal sealed interface HomeAction : UIAction {
    data class ClickBanner(val banner: Banner) : HomeAction

    data class ClickRollout(val rollout: Rollout) : HomeAction

    data object ClickRolloutMore : HomeAction

    data class ClickCafe(val cafe: Cafe) : HomeAction

    data object ClickCafeMore : HomeAction

    data class ClickCategoryDepth(
        val depth1Category: CategoryDepth1,
        val depth2Category: CategoryDepth2
    ) : HomeAction

}

internal sealed interface HomeIntent : UIIntent {
    data object Initialize : HomeIntent

    data class MoveToBanner(val banner: Banner) : HomeIntent

    data class MoveToDetail(val productId: Int) : HomeIntent

    data object MoveToRollout : HomeIntent

    data object MoveToCategoryDetail : HomeIntent
}

internal data class HomeState(
    val uiModels: List<HomeUiModel> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val rolloutList: List<Rollout> = emptyList(),
    val cafeList: List<Cafe> = emptyList()
) : UIState

internal sealed interface HomeEffect : UIEffect {

}

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    getRolloutUseCase: GetRolloutUseCase,
    getCafeMenuUseCase: GetCafeMenuUseCase
) : BaseViewModel<HomeAction, HomeIntent, HomeState, HomeEffect>(
    initialState = HomeState()
) {
    private val rolloutFlow = getRolloutUseCase().mapMerge(
        onSuccess = {
            setState { copy(rolloutList = it) }
        }
    ).stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkResult.Loading
    )

    private val cafeFlow = getCafeMenuUseCase().mapMerge(
        onSuccess = { setState { copy(cafeList = it) } }
    ).stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkResult.Loading
    )

    private val uiModelFlow =
        snapshotFlow { uiState }.filter { it.cafeList.isNotEmpty() && it.rolloutList.isNotEmpty() }
            .onEach {
                handleUiModel()
            }.launchIn(viewModelScope)

    init {
        rolloutFlow.launchIn(scope = viewModelScope)
        cafeFlow.launchIn(viewModelScope)
    }

    override suspend fun actionPredicate(action: HomeAction): HomeIntent = when (action) {
        is HomeAction.ClickBanner -> HomeIntent.MoveToBanner(action.banner)
        is HomeAction.ClickRollout -> HomeIntent.MoveToDetail(action.rollout.id)
            .also {
                analyticsLogger.logEvent(
                    LogName.CLICK_NEW_PRODUCT, mapOf(
                        LogProperty.PRODUCT_ID to action.rollout.id
                    )
                )
            }

        is HomeAction.ClickRolloutMore -> HomeIntent.MoveToRollout.also {
            analyticsLogger.logEvent(LogName.CLICK_NEW_PRODUCT_MORE)
        }

        is HomeAction.ClickCafe -> HomeIntent.MoveToDetail(action.cafe.id).also {
            analyticsLogger.logEvent(
                LogName.CLICK_CAFE_PRODUCT, mapOf(
                    LogProperty.PRODUCT_ID to action.cafe.id
                )
            )
        }

        else -> HomeIntent.Initialize
    }

    private fun handleUiModel() = setState {
        val mutableItemList = mutableListOf<HomeUiModel>()
        mutableItemList.add(HomeUiModel.Rollouts(rolloutList))
        mutableItemList.add(HomeUiModel.Cafes(cafeList))
        copy(uiModels = mutableItemList)
    }
}