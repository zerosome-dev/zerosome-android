package com.zerosome.review

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.model.Review
import com.zerosome.product.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal sealed interface ReviewAction: UIAction {
    data class GetProductId(val id: Int): ReviewAction

    data object ClickWriteReview: ReviewAction

    data object ScrollToBottom: ReviewAction
}

internal sealed interface ReviewIntent: UIIntent {
    data class Initialize(val id: Int): ReviewIntent

    data object WriteReview: ReviewIntent

    data object LoadMore: ReviewIntent
}

internal data class ReviewState(
    val productId: Int? = null,
    val reviewList: List<Review> = emptyList()
): UIState

internal sealed interface ReviewEffect: UIEffect {
    data object MoveToWriteReview: ReviewEffect
}
@HiltViewModel
internal class ReviewViewModel @Inject constructor(
    private val getReviewUseCase: GetReviewUseCase,
): BaseViewModel<ReviewAction, ReviewIntent, ReviewState, ReviewEffect>(
    initialState = ReviewState()
) {
    val reviewFlow = getReviewUseCase.getCurrentReviews().mapMerge().onEach {
        setState { copy(reviewList = it ?: emptyList()) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    ).launchIn(viewModelScope)

    override fun actionPredicate(action: ReviewAction): ReviewIntent =
        when (action) {
            is ReviewAction.GetProductId -> ReviewIntent.Initialize(action.id)
            is ReviewAction.ClickWriteReview -> ReviewIntent.WriteReview
            is ReviewAction.ScrollToBottom -> ReviewIntent.LoadMore
        }

    override fun collectIntent(intent: ReviewIntent) {
        when (intent) {
            is ReviewIntent.Initialize -> setState { copy(productId = intent.id) }
            is ReviewIntent.WriteReview -> setEffect {
                ReviewEffect.MoveToWriteReview
            }
            is ReviewIntent.LoadMore -> getReviewUseCase.refresh()
        }
    }
}