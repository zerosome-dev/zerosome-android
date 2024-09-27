package com.zerosome.onboarding

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
) : UIState

internal sealed interface OnboardingEffect : UIEffect {
    data object NavigateToMain : OnboardingEffect
}

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<OnboardingAction, OnboardingIntent, OnboardingState, OnboardingEffect>(
    initialState = OnboardingState()
) {

    private val confirmable =
        snapshotFlow { uiState }.filter { it.nickname.isNotEmpty() && it.userToken.isNotEmpty() }
            .onEach {
                setIntent(OnboardingIntent.Confirm)
            }

    init {
        viewModelScope.launch {
            confirmable.collect()
        }
    }

    override fun actionPredicate(action: OnboardingAction): OnboardingIntent {
        return when (action) {
            is OnboardingAction.UserDateReceived -> OnboardingIntent.SetUserToken(
                accessToken = action.accessToken,
                userType = action.userType
            )

            is OnboardingAction.UserMarketingAgreed -> OnboardingIntent.UserMarketingAgreed(agreed = action.isAgreed)
            is OnboardingAction.UserNicknameSet -> OnboardingIntent.SetNickname(nickname = action.nickname)
        }
    }

    override fun collectIntent(intent: OnboardingIntent) {
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

    private fun signUp() = withState {
        signUpUseCase(
            socialType = userType.name,
            socialToken = userToken,
            nickname = nickname,
            marketingAgreed = userMarketingAgreed
        ).mapMerge().filterNotNull()
            .collect {
                setEffect { OnboardingEffect.NavigateToMain }
            }
    }
}