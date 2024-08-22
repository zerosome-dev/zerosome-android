package com.zerosome.review

import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.model.Product
import com.zerosome.product.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


internal sealed interface ReviewWriteAction: UIAction {
    data class WriteReview(val text: String): ReviewWriteAction

    data class ClickReviewScore(val score: Int): ReviewWriteAction

    data object ClickConfirmButton: ReviewWriteAction
}

internal sealed interface ReviewWriteIntent: UIIntent {
    data class SetReviewText(val text: String): ReviewWriteIntent

    data class SetReviewScore(val score: Int): ReviewWriteIntent

    data object Confirm: ReviewWriteIntent
}

internal data class ReviewWriteState(
    val reviewText: String = "",
    val reviewScore: Int = 5,
    val selectedProduct: Product? = null,

): UIState

internal sealed interface ReviewWriteEffect: UIEffect {
    data object PopToBackStack: ReviewWriteEffect
}


@HiltViewModel
internal class ReviewWriteViewModel @Inject constructor(
    productDetailUseCase: GetProductDetailUseCase,
    private val createReviewUseCase: CreateReviewUseCase,
): BaseViewModel<ReviewWriteAction, ReviewWriteIntent, ReviewWriteState, ReviewWriteEffect>(
    initialState = ReviewWriteState()
) {
    private val productFlow = productDetailUseCase.getCurrentProduct().mapMerge().onEach {
        setState { copy(selectedProduct = it) }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    ).launchIn(viewModelScope)

    override fun actionPredicate(action: ReviewWriteAction): ReviewWriteIntent = when(action) {
        is ReviewWriteAction.WriteReview -> ReviewWriteIntent.SetReviewText(action.text)
        is ReviewWriteAction.ClickReviewScore -> ReviewWriteIntent.SetReviewScore(action.score)
        ReviewWriteAction.ClickConfirmButton -> ReviewWriteIntent.Confirm
    }

    override fun collectIntent(intent: ReviewWriteIntent) {
        when (intent) {
            is ReviewWriteIntent.SetReviewText -> setState { copy(reviewText = intent.text) }
            is ReviewWriteIntent.SetReviewScore -> setState { copy(reviewScore = intent.score) }
            is ReviewWriteIntent.Confirm -> addReview()
        }
    }

    private fun addReview() = withState {
        val product = requireNotNull(selectedProduct)
        viewModelScope.launch {
            createReviewUseCase(product.productId, reviewText, reviewScore)
            setEffect { ReviewWriteEffect.PopToBackStack }
        }
    }

}