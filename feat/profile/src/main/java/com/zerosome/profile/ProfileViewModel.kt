package com.zerosome.profile

import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.onboarding.CheckUserUseCase
import com.zerosome.onboarding.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

internal sealed interface ProfileAction: UIAction {
    data object ClickNicknameChange: ProfileAction

    data object ClickReview: ProfileAction

    data object ClickNotice: ProfileAction

    data object ClickFAQ: ProfileAction

    data object ClickTerms: ProfileAction

    data object ClickPrivacy: ProfileAction

    data object ClickLogout: ProfileAction

    data object ClickRevoke: ProfileAction

    data object ClickOpenKakao: ProfileAction
}

internal sealed interface ProfileIntent: UIIntent {
    data class Navigate(val route: ProfileNavRoute): ProfileIntent

    data object Logout: ProfileIntent

    data object RevokeAccess: ProfileIntent
}

data class ProfileState(
    val profile: Int? = null
): UIState

internal sealed interface ProfileEffect: UIEffect {
    data object MoveToWeb: ProfileEffect

    data object MoveToNicknameChange: ProfileEffect

    data object MoveToLogin: ProfileEffect
}

internal enum class ProfileNavRoute {
    REVIEW, NOTICE, FAQ, TERMS, PRIVACY, NICKNAME
}

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val checkUserUseCase: CheckUserUseCase,
    private val logoutUseCase: LogoutUseCase
): BaseViewModel<ProfileAction, ProfileIntent, ProfileState, ProfileEffect>(
    initialState = ProfileState()
){
    override fun actionPredicate(action: ProfileAction): ProfileIntent = ProfileIntent.Logout

    override fun collectIntent(intent: ProfileIntent) {}

}