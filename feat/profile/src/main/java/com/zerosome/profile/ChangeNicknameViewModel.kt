package com.zerosome.profile

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.zerosome.feat.core.BaseViewModel
import com.zerosome.feat.core.UIAction
import com.zerosome.feat.core.UIEffect
import com.zerosome.feat.core.UIIntent
import com.zerosome.feat.core.UIState
import com.zerosome.onboarding.ValidateNicknameUseCase
import com.zerosome.onboarding.ValidateReason
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal sealed interface ChangeNicknameAction : UIAction {
    data object Initialize : ChangeNicknameAction
    data class WriteNickname(val nickname: String) : ChangeNicknameAction

    data object ClickConfirm : ChangeNicknameAction
}

internal sealed interface ChangeNicknameIntent : UIIntent {
    data object Initialize : ChangeNicknameIntent

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
                ValidateReason.SUCCESS -> com.zerosome.design.R.string.screen_change_nickname_textfield_positive
                ValidateReason.NOT_VALIDATED -> com.zerosome.design.R.string.screen_nickname_textfield_negative_validation
                ValidateReason.NOT_VERIFIED -> com.zerosome.design.R.string.screen_nickname_textfield_negative
            }
        }

    val isConfirmAvailable = run {
        selectedNickname != previousNickname && selectedNickname.isNotEmpty()
    }

}

internal sealed interface ChangeNicknameEffect : UIEffect

@HiltViewModel
internal class ChangeNicknameViewModel @Inject constructor(
    private val validateNicknameUseCase: ValidateNicknameUseCase,
) : BaseViewModel<ChangeNicknameAction, ChangeNicknameIntent, ChangeNicknameState, ChangeNicknameEffect>(
    initialState = ChangeNicknameState()
) {

    private val textFlow = snapshotFlow { uiState }.debounce(200)
        .filter { it.isConfirmAvailable }.onEach {
            validateNicknameUseCase += it.selectedNickname
        }.launchIn(viewModelScope)


    val validateResult = validateNicknameUseCase().mapMerge {
        setState { copy(isVerified = it == ValidateReason.SUCCESS, validateReason = it) }
    }

    init {
        setAction(ChangeNicknameAction.Initialize)
    }

    override suspend fun actionPredicate(action: ChangeNicknameAction): ChangeNicknameIntent {
        return when (action) {
            is ChangeNicknameAction.Initialize -> ChangeNicknameIntent.Initialize
            is ChangeNicknameAction.ClickConfirm -> ChangeNicknameIntent.Confirm
            is ChangeNicknameAction.WriteNickname -> ChangeNicknameIntent.SetNickname(action.nickname)
        }
    }

    override suspend fun collectIntent(intent: ChangeNicknameIntent) {
        when (intent) {
            ChangeNicknameIntent.Initialize -> {
//                viewModelScope.launch { textFlow.collect() }
            }

            ChangeNicknameIntent.Confirm -> {
                // Change Nickname
            }

            is ChangeNicknameIntent.SetNickname -> setState { copy(selectedNickname = intent.nickname) }
        }
    }
}