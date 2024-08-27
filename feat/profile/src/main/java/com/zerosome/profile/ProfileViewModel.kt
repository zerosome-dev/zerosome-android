package com.zerosome.profile

import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.model.UserBasicInfo
import com.zerosome.domain.profile.GetUserDataUseCase
import com.zerosome.onboarding.CheckUserUseCase
import com.zerosome.onboarding.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed interface ProfileAction : UIAction {
    data object ClickNicknameChange : ProfileAction

    data object ClickReview : ProfileAction

    data object ClickNotice : ProfileAction

    data object ClickFAQ : ProfileAction

    data object ClickTerms : ProfileAction

    data object ClickPrivacy : ProfileAction

    data object ClickLogout : ProfileAction

    data object ClickRevoke : ProfileAction

    data object ClickOpenKakao : ProfileAction
}

internal sealed interface ProfileIntent : UIIntent {
    data class Navigate(val route: ProfileNavRoute) : ProfileIntent

    data object Logout : ProfileIntent

    data object RevokeAccess : ProfileIntent
}

data class ProfileState(
    val profile: UserBasicInfo? = null
) : UIState

internal sealed interface ProfileEffect : UIEffect {
    data object MoveToWeb : ProfileEffect

    data object MoveToNicknameChange : ProfileEffect

    data object MoveToLogin : ProfileEffect
}

internal enum class ProfileNavRoute {
    REVIEW, NOTICE, FAQ, TERMS, PRIVACY, NICKNAME
}

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val checkUserUseCase: CheckUserUseCase,
    getUserDataUseCase: GetUserDataUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<ProfileAction, ProfileIntent, ProfileState, ProfileEffect>(
    initialState = ProfileState()
) {

    private val userFlow = getUserDataUseCase().mapMerge().onEach {
        setState { copy(profile = it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    ).launchIn(viewModelScope)

    override fun actionPredicate(action: ProfileAction): ProfileIntent = when(action) {
        is ProfileAction.ClickLogout -> ProfileIntent.Logout
        is ProfileAction.ClickNicknameChange -> ProfileIntent.Navigate(ProfileNavRoute.NICKNAME)
        is ProfileAction.ClickTerms -> ProfileIntent.Navigate(ProfileNavRoute.TERMS)
        is ProfileAction.ClickReview -> ProfileIntent.Navigate(ProfileNavRoute.REVIEW)
        is ProfileAction.ClickNotice -> ProfileIntent.Navigate(ProfileNavRoute.NOTICE)
        is ProfileAction.ClickPrivacy -> ProfileIntent.Navigate(ProfileNavRoute.PRIVACY)
        is ProfileAction.ClickFAQ -> ProfileIntent.Navigate(ProfileNavRoute.FAQ)
        is ProfileAction.ClickRevoke -> ProfileIntent.RevokeAccess
        is ProfileAction.ClickOpenKakao -> ProfileIntent.Logout
    }

    override fun collectIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.Logout -> viewModelScope.launch {
                logoutUseCase().collect {
                    if (it) {
                        setEffect { ProfileEffect.MoveToLogin }
                    }
                }
            }
            is ProfileIntent.Navigate -> {}
            is ProfileIntent.RevokeAccess -> {}

        }
    }

}