package com.zerosome.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.onboarding.CheckUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashAction: UIAction

sealed interface SplashIntent: UIIntent {
    data object Initialize: SplashIntent
}

data object SplashState: UIState

sealed interface SplashEffect: UIEffect {
    data object MoveToLogin: SplashEffect

    data object MoveToMain: SplashEffect
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUserUseCase: CheckUserUseCase
) : BaseViewModel<SplashAction, SplashIntent, SplashState, SplashEffect>(
    initialState = SplashState
) {

    init {
        setIntent(SplashIntent.Initialize)
    }
    override fun actionPredicate(action: SplashAction): SplashIntent = SplashIntent.Initialize

    override fun collectIntent(intent: SplashIntent) {
        when(intent) {
            is SplashIntent.Initialize -> {
                viewModelScope.launch {
                    delay(1000)
                    checkUserUseCase().collect {
                        if (it) {
                            setEffect { SplashEffect.MoveToMain }
                        } else {
                            setEffect { SplashEffect.MoveToLogin }
                        }
                    }
                }
            }
        }
    }
}