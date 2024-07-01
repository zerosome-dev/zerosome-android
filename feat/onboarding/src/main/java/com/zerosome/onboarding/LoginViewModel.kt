package com.zerosome.onboarding

import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

internal sealed interface LoginAction: UIAction {
    data object ClickKakaoLogin: LoginAction

    data object ClickSkip: LoginAction
}

internal sealed interface LoginIntent: UIIntent {
    data object LoginWithKakao: LoginIntent

    data object SkipLogin: LoginIntent
}

internal data class LoginState(
    val isAlreadySignedUp: Boolean? = null,
): UIState

internal sealed interface LoginEffect: UIEffect {

    data object OpenKakao: LoginEffect

    data object NavigateToMain: LoginEffect

    data class NavigateToTermsAgree(val accessToken: String): LoginEffect

}

@HiltViewModel
internal class LoginViewModel @Inject constructor(): BaseViewModel<LoginAction, LoginIntent, LoginState, LoginEffect>(
    initialState = LoginState()
){
    override fun actionPredicate(action: LoginAction): LoginIntent =
        when (action) {
            LoginAction.ClickKakaoLogin -> LoginIntent.LoginWithKakao
            LoginAction.ClickSkip -> LoginIntent.SkipLogin
        }

    override fun collectIntent(intent: LoginIntent) {
        when (intent) {
            LoginIntent.LoginWithKakao -> setEffect { LoginEffect.OpenKakao }
            LoginIntent.SkipLogin -> setEffect { LoginEffect.NavigateToMain }
        }
    }
}