package com.zerosome.main.home

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.model.Banner
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.model.Rollout
import com.zerosome.product.GetCafeMenuUseCase
import com.zerosome.product.GetRolloutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed interface HomeAction: UIAction {
    data class ClickBanner(val banner: Banner): HomeAction

    data class ClickRollout(val rollout: Rollout): HomeAction

    data object ClickRolloutMore: HomeAction

    data class ClickCafe(val cafe: Cafe): HomeAction

    data object ClickCafeMore: HomeAction

    data class ClickCategoryDepth(val depth1Category: CategoryDepth1, val depth2Category: CategoryDepth2): HomeAction

}

internal sealed interface HomeIntent: UIIntent {
    data object Initialize: HomeIntent

    data class MoveToBanner(val banner: Banner): HomeIntent

    data class MoveToDetail(val productId: Int): HomeIntent

    data object MoveToRollout: HomeIntent

    data object MoveToCategoryDetail: HomeIntent
}

internal data class HomeState(
    val uiModels: List<HomeUiModel> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val rolloutList: List<Rollout> = emptyList(),
    val cafeList: List<Cafe> = emptyList()
): UIState

internal sealed interface HomeEffect: UIEffect {

}

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    getRolloutUseCase: GetRolloutUseCase,
    getCafeMenuUseCase: GetCafeMenuUseCase
): BaseViewModel<HomeAction, HomeIntent, HomeState, HomeEffect>(
    initialState = HomeState()
){
    private val rolloutFlow = getRolloutUseCase().mapMerge()
        .onEach {
            Log.d("CPRI", "$it")
            setState {
                copy(rolloutList = it ?: emptyList())
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val cafeFlow = getCafeMenuUseCase().mapMerge()
        .onEach {
            Log.d("CPRI", "$it")
            setState {
                copy(cafeList = it ?: emptyList())
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val uiModelFlow = snapshotFlow { uiState }.filter { it.cafeList.isNotEmpty() && it.rolloutList.isNotEmpty() }.onEach {
        handleUiModel()
    }

    init {
        viewModelScope.launch {
            uiModelFlow.collect()
        }
        setIntent(HomeIntent.Initialize)
    }

    override fun actionPredicate(action: HomeAction): HomeIntent = when(action) {
        is HomeAction.ClickBanner -> HomeIntent.MoveToBanner(action.banner)
        is HomeAction.ClickRollout -> HomeIntent.MoveToDetail(action.rollout.id)
        is HomeAction.ClickRolloutMore -> HomeIntent.MoveToRollout
        is HomeAction.ClickCafe -> HomeIntent.MoveToDetail(action.cafe.id)
        else -> HomeIntent.Initialize
    }

    override fun collectIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Initialize -> {
                viewModelScope.launch {
                    rolloutFlow.collect()
                }
                viewModelScope.launch {
                    cafeFlow.collect()
                }
            }
            else -> {}
        }
    }


    private fun handleUiModel() = withState {
        val mutableItemList = mutableListOf<HomeUiModel>()
        mutableItemList.add(HomeUiModel.Rollouts(rolloutList))
        mutableItemList.add(HomeUiModel.Cafes(cafeList))
        setState { copy(uiModels = mutableItemList) }
    }
}