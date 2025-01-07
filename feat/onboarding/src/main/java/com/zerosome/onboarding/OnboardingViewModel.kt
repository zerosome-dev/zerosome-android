package com.zerosome.onboarding

import androidx.lifecycle.viewModelScope
import com.zerosome.feat.core.BaseViewModel
import com.zerosome.feat.core.UIAction
import com.zerosome.feat.core.UIEffect
import com.zerosome.feat.core.UIIntent
import com.zerosome.feat.core.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

internal sealed interface OnboardingAction : UIAction {
    data class UserDateReceived(val accessToken: String, val userType: LoginType) : OnboardingAction

    data class UserMarketingAgreed(val isAgreed: Boolean) : OnboardingAction

    data class UserNicknameSet(val nickname: String) : OnboardingAction
}

internal sealed interface OnboardingIntent : UIIntent {
    data class SetUserToken(val accessToken: String, val userType: LoginType) : OnboardingIntent

    data class UserMarketingAgreed(val agreed: Boolean) : OnboardingIntent

    data class SetNickname(val nickname: String) : OnboardingIntent

    data object Confirm : OnboardingIntent
}

internal data class OnboardingState(
    val userToken: String = "",
    val userType: LoginType = LoginType.NONE,
    val userMarketingAgreed: Boolean = false,
    val nickname: String = ""
) : UIState {
    val confirmable: Boolean = run {
        nickname.isNotEmpty() && userToken.isNotEmpty()
    }
}

internal sealed interface OnboardingEffect : UIEffect {
    data object NavigateToMain : OnboardingEffect
}

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<OnboardingAction, OnboardingIntent, OnboardingState, OnboardingEffect>(
    initialState = OnboardingState()
) {

    private val signUpResultUseCase = signUpUseCase()
        .mapMerge { setEffect { OnboardingEffect.NavigateToMain } }
        .launchIn(viewModelScope)

    override suspend fun actionPredicate(action: OnboardingAction): OnboardingIntent {
        return when (action) {
            is OnboardingAction.UserDateReceived -> OnboardingIntent.SetUserToken(
                accessToken = action.accessToken,
                userType = action.userType
            )

            is OnboardingAction.UserMarketingAgreed -> OnboardingIntent.UserMarketingAgreed(agreed = action.isAgreed)
            is OnboardingAction.UserNicknameSet -> OnboardingIntent.SetNickname(nickname = action.nickname)
        }
    }

    override suspend fun collectIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.SetUserToken -> setState {
                copy(
                    userType = intent.userType,
                    userToken = intent.accessToken
                )
            }

            is OnboardingIntent.SetNickname -> setState { copy(nickname = intent.nickname) }
            is OnboardingIntent.UserMarketingAgreed -> setState { copy(userMarketingAgreed = intent.agreed) }
            is OnboardingIntent.Confirm -> signUp()
        }
    }

    private fun signUp() = with(uiState) {
        signUpUseCase.plusAssign(userToken, userType.name, nickname, userMarketingAgreed)
    }
}