package com.zerosome.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zerosome.domain.NetworkResult
import com.zerosome.feat.core.BaseViewModel
import com.zerosome.feat.core.UIAction
import com.zerosome.feat.core.UIEffect
import com.zerosome.feat.core.UIIntent
import com.zerosome.feat.core.UIState
import com.zerosome.onboarding.CheckUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashAction : UIAction {
    data object Initializer : SplashAction
}

sealed interface SplashIntent : UIIntent {
    data object Initialize : SplashIntent
}

data object SplashState : UIState

sealed interface SplashEffect : UIEffect {
    data object MoveToLogin : SplashEffect

    data object MoveToMain : SplashEffect
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUserUseCase: CheckUserUseCase
) : BaseViewModel<SplashAction, SplashIntent, SplashState, SplashEffect>(
    initialState = SplashState
) {
    private val checkUser = checkUserUseCase().mapMerge(
        { it.printStackTrace() }, {
        Log.d("CPRI", "RESULT")
        if (it) {
            setEffect { SplashEffect.MoveToMain }
        } else {
            setEffect { SplashEffect.MoveToLogin }
        }
    }).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkResult.Loading
    )

    init {
        setAction(SplashAction.Initializer)
        checkUser.launchIn(viewModelScope)
    }

    override suspend fun actionPredicate(action: SplashAction): SplashIntent =
        SplashIntent.Initialize

    override suspend fun collectIntent(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.Initialize -> {
                viewModelScope.launch {
                    delay(1000)
                    +checkUserUseCase
                }
            }
        }
    }
}