package com.zerosome.onboarding

import androidx.lifecycle.viewModelScope
import com.zerosome.core.constants.ClientError
import com.zerosome.feat.core.BaseViewModel
import com.zerosome.feat.core.UIAction
import com.zerosome.feat.core.UIEffect
import com.zerosome.feat.core.UIIntent
import com.zerosome.feat.core.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed interface LoginAction : UIAction {
    data object ClickKakaoLogin : LoginAction

    data object ClickSkip : LoginAction

    data class CheckKakaoLogin(val accessToken: String) : LoginAction

    data class CheckAppleLogin(val accessToken: String) : LoginAction
}

internal sealed interface LoginIntent : UIIntent {
    data object LoginWithKakao : LoginIntent

    data object SkipLogin : LoginIntent

    data class CheckUser(val loginType: LoginType, val accessToken: String) : LoginIntent


}

internal data class LoginState(
    val userToken: LoginType? = null,
    val accessToken: String = "",
    val isAlreadySignedUp: Boolean? = null,
) : UIState

internal sealed interface LoginEffect : UIEffect {

    data object OpenKakao : LoginEffect

    data object NavigateToMain : LoginEffect

    data class NavigateToTermsAgree(val accessToken: String, val userType: LoginType) : LoginEffect
}

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginAction, LoginIntent, LoginState, LoginEffect>(
    initialState = LoginState()
) {
    override suspend fun actionPredicate(action: LoginAction): LoginIntent =
        when (action) {
            LoginAction.ClickKakaoLogin -> LoginIntent.LoginWithKakao
            LoginAction.ClickSkip -> LoginIntent.SkipLogin
            is LoginAction.CheckAppleLogin -> LoginIntent.CheckUser(
                loginType = LoginType.APPLE,
                accessToken = action.accessToken
            )

            is LoginAction.CheckKakaoLogin -> LoginIntent.CheckUser(
                loginType = LoginType.KAKAO,
                accessToken = action.accessToken
            )
        }

    private val loginFlow = loginUseCase().mapMerge(onSuccess = {
        setEffect { LoginEffect.NavigateToMain }
    }, onFailure = {
        if (it.clientError == ClientError.LOGIN_NOT_VALIDATED_EXCEPTION) {
            setEffect {
                LoginEffect.NavigateToTermsAgree(
                    uiState.accessToken,
                    userType = requireNotNull(uiState.userToken)
                )
            }
        }
    })

    init {
        init(loginFlow)
    }

    override suspend fun collectIntent(intent: LoginIntent) {
        when (intent) {
            LoginIntent.LoginWithKakao -> setEffect { LoginEffect.OpenKakao }
            LoginIntent.SkipLogin -> setEffect { LoginEffect.NavigateToMain }
            is LoginIntent.CheckUser -> userLogin(
                accessToken = intent.accessToken,
                userType = intent.loginType
            ).also {
                setState { copy(userToken = intent.loginType, accessToken = accessToken) }
            }
        }
    }

    private fun userLogin(accessToken: String, userType: LoginType) {
        loginUseCase.plusAssign(accessToken, userType)
    }
}
