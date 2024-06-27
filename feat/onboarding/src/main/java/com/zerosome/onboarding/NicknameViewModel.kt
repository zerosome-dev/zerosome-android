package com.zerosome.onboarding

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed interface NicknameAction : UIAction {
    data class SetNickname(val name: String) : NicknameAction
}

internal sealed interface NicknameIntent : UIIntent {
    data class SetNickname(val nickname: String) : NicknameIntent
}

internal data class NicknameState(
    val nickname: String = "",
    val isConfirmed: Boolean? = null,
    val throwable: Throwable? = null
) : UIState {
    val holderTextResId: Int?
        get() = isConfirmed?.let {
            if (it) {
                com.zerosome.onboarding.R.string.screen_nickname_textfield_positive
            } else {
                com.zerosome.onboarding.R.string.screen_nickname_textfield_negative
            }
        }

    val canGoNext: Boolean
        get() = nickname.isNotEmpty() && isConfirmed == true
}

internal sealed interface NicknameEffect : UIEffect

@HiltViewModel
internal class NicknameViewModel @Inject constructor(
    validateNicknameUseCase: ValidateNicknameUseCase
) : BaseViewModel<NicknameAction, NicknameIntent, NicknameState, NicknameEffect>(
    initialState = NicknameState()
) {

    private val nicknameFlow = uiState.map { it.nickname }.distinctUntilChanged().onEach {
        setState {
            copy(isConfirmed = null)
        }
    }.filter { it.isNotEmpty() }.debounce(1000)
        .flatMapConcat { validateNicknameUseCase(it) }
        .mapMerge()
        .onEach {
            setState { copy(isConfirmed = it) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        viewModelScope.launch {
            nicknameFlow.collect()
        }
    }

    override fun actionPredicate(action: NicknameAction): NicknameIntent =
        when (action) {
            is NicknameAction.SetNickname -> NicknameIntent.SetNickname(action.name)
        }

    override fun collectIntent(intent: NicknameIntent) {
        when (intent) {
            is NicknameIntent.SetNickname -> setState {
                copy(nickname = intent.nickname)
            }
        }
    }

}