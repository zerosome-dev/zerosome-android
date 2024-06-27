package com.zerosome.onboarding

import androidx.compose.ui.res.stringResource
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

internal sealed interface TermsAction : UIAction {
    data object ClickAll : TermsAction

    data object ClickService : TermsAction

    data object ClickPrivacy : TermsAction

    data object ClickMarketing : TermsAction
}

internal sealed interface TermsIntent : UIIntent {
    data object SetAll : TermsIntent
    data object SetService : TermsIntent

    data object SetPrivacy : TermsIntent

    data object SetMarketing : TermsIntent
}

internal data class TermsState(
    val serviceTermsAgreed: Boolean = false,
    val privacyTermsAgreed: Boolean = false,
    val marketingTermsAgreed: Boolean = false
) : UIState {
    val allChecked: Boolean
        get() = serviceTermsAgreed && privacyTermsAgreed && marketingTermsAgreed

    val canGoNext: Boolean
        get() = serviceTermsAgreed && privacyTermsAgreed
}

internal sealed interface TermsEffect : UIEffect {

}

@HiltViewModel
internal class TermsViewModel @Inject constructor() :
    BaseViewModel<TermsAction, TermsIntent, TermsState, TermsEffect>(
        initialState = TermsState()
    ) {
    override fun actionPredicate(action: TermsAction): TermsIntent =
        when (action) {
            TermsAction.ClickAll -> TermsIntent.SetAll
            TermsAction.ClickService -> TermsIntent.SetService
            TermsAction.ClickPrivacy -> TermsIntent.SetPrivacy
            TermsAction.ClickMarketing -> TermsIntent.SetMarketing
        }


    override fun collectIntent(intent: TermsIntent) {
        when (intent){
            TermsIntent.SetAll -> setState {
                if (allChecked) {
                    copy(serviceTermsAgreed = false, privacyTermsAgreed = false, marketingTermsAgreed = false)
                } else {
                    copy(serviceTermsAgreed = true, privacyTermsAgreed = true, marketingTermsAgreed = true)
                }
            }
            TermsIntent.SetService -> setState { copy(serviceTermsAgreed = serviceTermsAgreed.not()) }
            TermsIntent.SetPrivacy -> setState { copy(privacyTermsAgreed = privacyTermsAgreed.not()) }
            TermsIntent.SetMarketing -> setState { copy(marketingTermsAgreed = marketingTermsAgreed.not()) }
        }
    }

}