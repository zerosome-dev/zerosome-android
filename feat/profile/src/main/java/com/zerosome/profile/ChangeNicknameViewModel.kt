package com.zerosome.profile

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.onboarding.ValidateNicknameUseCase
import com.zerosome.onboarding.ValidateReason
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed interface ChangeNicknameAction : UIAction {
    data class WriteNickname(val nickname: String) : ChangeNicknameAction

    data object ClickConfirm : ChangeNicknameAction
}

internal sealed interface ChangeNicknameIntent : UIIntent {
    data object Initialize: ChangeNicknameIntent

    data class SetNickname(val nickname: String) : ChangeNicknameIntent

    data object Confirm : ChangeNicknameIntent
}

internal data class ChangeNicknameState(
    val previousNickname: String = "",
    val selectedNickname: String = "",
    val isValidated: Boolean? = null,
    val isVerified: Boolean? = null,
    val validateReason: ValidateReason? = null
) : UIState {
    val holderTextResId: Int?
        get() = validateReason?.let {
            when (it) {
                ValidateReason.SUCCESS -> com.zerosome.profile.R.string.screen_change_nickname_textfield_positive
                ValidateReason.NOT_VALIDATED -> com.zerosome.profile.R.string.screen_change_nickname_textfield_negative_validation
                ValidateReason.NOT_VERIFIED -> com.zerosome.profile.R.string.screen_change_nickname_textfield_negative
            }
        }
}

internal sealed interface ChangeNicknameEffect : UIEffect

@HiltViewModel
internal class ChangeNicknameViewModel @Inject constructor(
    private val validateNicknameUseCase: ValidateNicknameUseCase,
) : BaseViewModel<ChangeNicknameAction, ChangeNicknameIntent, ChangeNicknameState, ChangeNicknameEffect>(
    initialState = ChangeNicknameState()
) {

    private val textFlow = snapshotFlow { uiState.selectedNickname }.debounce(200)
        .filter { it != uiState.previousNickname }.filter { it.isNotEmpty() }
        .flatMapLatest { validateNicknameUseCase(it) }.mapMerge().onEach {
            setState { copy(isVerified = it == ValidateReason.SUCCESS, validateReason = it) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        setIntent(ChangeNicknameIntent.Initialize)
    }

    override fun actionPredicate(action: ChangeNicknameAction): ChangeNicknameIntent {
        return when (action) {
            ChangeNicknameAction.ClickConfirm -> ChangeNicknameIntent.Confirm
            is ChangeNicknameAction.WriteNickname -> ChangeNicknameIntent.SetNickname(action.nickname)
        }
    }

    override fun collectIntent(intent: ChangeNicknameIntent) {
        when (intent) {
            ChangeNicknameIntent.Initialize -> {
                viewModelScope.launch { textFlow.collect() }
            }
            ChangeNicknameIntent.Confirm -> {
                // Change Nickname
            }
            is ChangeNicknameIntent.SetNickname -> setState { copy(selectedNickname = intent.nickname) }
        }
    }
}