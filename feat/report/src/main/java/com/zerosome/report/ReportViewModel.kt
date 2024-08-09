package com.zerosome.report

import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.model.ReportReason
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

internal sealed interface ReportAction : UIAction {
    data class ClickSelectReason(val reason: ReportReason) : ReportAction

    data class WriteReasonDescription(val description: String) : ReportAction

    data object ClickConfirmButton : ReportAction
}

internal sealed interface ReportIntent : UIIntent {
    data class SetReason(val reason: ReportReason) : ReportIntent

    data class SetDescription(val description: String) : ReportIntent

    data object Confirm : ReportIntent
}

internal data class ReportState(
    val selectedReason: ReportReason? = null,
    val reasonDescription: String = ""
) : UIState

internal sealed interface ReportEffect : UIEffect

@HiltViewModel
internal class ReportViewModel @Inject constructor() :
    BaseViewModel<ReportAction, ReportIntent, ReportState, ReportEffect>(
        initialState = ReportState()
    ) {
    override fun actionPredicate(action: ReportAction): ReportIntent {
        return when (action) {
            is ReportAction.ClickSelectReason -> ReportIntent.SetReason(action.reason)
            is ReportAction.WriteReasonDescription -> ReportIntent.SetDescription(action.description)
            ReportAction.ClickConfirmButton -> ReportIntent.Confirm
        }
    }

    override fun collectIntent(intent: ReportIntent) {
        when (intent) {
            is ReportIntent.SetReason -> setState {
                copy(
                    selectedReason = intent.reason,
                    reasonDescription = ""
                )
            }

            is ReportIntent.SetDescription -> setState { copy(reasonDescription = intent.description) }
            ReportIntent.Confirm -> report()
        }
    }

    private fun report() {

    }
}