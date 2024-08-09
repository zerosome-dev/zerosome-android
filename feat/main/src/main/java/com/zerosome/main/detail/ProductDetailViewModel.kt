package com.zerosome.main.detail

import androidx.compose.runtime.snapshotFlow
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed interface ProductDetailAction: UIAction {
    data class ViewCreated(val id: String): ProductDetailAction

    data object ClickNutrients: ProductDetailAction

    data class ClickReview(val reviewId: Int?): ProductDetailAction

    data object ClickReviewWrite: ProductDetailAction

    data class ClickSimilarProduct(val productId: String): ProductDetailAction
}

internal sealed interface ProductDetailIntent: UIIntent {
    data class Initialize(val id: String): ProductDetailIntent

    data class SelectSimilarId(val productId: String): ProductDetailIntent

    data class SelectReview(val reviewId: Int?): ProductDetailIntent

    data object WriteReview: ProductDetailIntent

    data object SeeNutrients: ProductDetailIntent
}

internal data class ProductDetailState(
    val productId: String = "",
    val uiModel: List<ProductDetailUiModel> = emptyList(),
    val selectedProduct: Product? = null
): UIState

internal sealed interface ProductDetailEffect: UIEffect {
    data class NavigateToSimilarProduct(val productId: String): ProductDetailEffect

    data class NavigateToReview(val reviewId: Int?): ProductDetailEffect

    data object NavigateToReviewWrite: ProductDetailEffect

    data object OpenNutrientDialog: ProductDetailEffect
}

@HiltViewModel
internal class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
): BaseViewModel<ProductDetailAction, ProductDetailIntent, ProductDetailState, ProductDetailEffect>(
    initialState = ProductDetailState()
) {

    private val productIdFlow = snapshotFlow { uiState.productId }.filter { it.isEmpty() }.flatMapConcat {
        getProductDetailUseCase(it)
    }.mapMerge().onEach {
        setState {
            copy(selectedProduct = it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private val productFlow = snapshotFlow { uiState.selectedProduct }.filter { it == null }.onEach {
        handleUiModel()
    }

    init {
        viewModelScope.launch {
            productIdFlow.collect()
        }

        viewModelScope.launch {
            productFlow.collect()
        }
    }

    private fun handleUiModel() = withState {
        val uiModelList = mutableListOf<ProductDetailUiModel>()
        selectedProduct?.let {
            uiModelList.add(ProductDetailUiModel.Introduction(it.image, it.brandName, it.productName, it.rating, it.reviewCount))
            uiModelList.add(ProductDetailUiModel.Nutrient)
            uiModelList.add(ProductDetailUiModel.Stores(it.onlineStoreList, it.offlineStoreList))
            if (it.reviewCount > 0) {
                uiModelList.add(ProductDetailUiModel.Reviews(it.reviewCount, it.rating, it.reviewThumbnailList))
            } else {
                uiModelList.add(ProductDetailUiModel.EmptyReview)
            }
            uiModelList.add(ProductDetailUiModel.SimilarProducts(it.relatedProductList))
        }
        setState {
            copy(uiModel = uiModelList)
        }
    }

    override fun actionPredicate(action: ProductDetailAction): ProductDetailIntent =
        when(action) {
            is ProductDetailAction.ViewCreated -> ProductDetailIntent.Initialize(action.id)
            is ProductDetailAction.ClickReview -> ProductDetailIntent.SelectReview(action.reviewId)
            is ProductDetailAction.ClickSimilarProduct -> ProductDetailIntent.SelectSimilarId(action.productId)
            is ProductDetailAction.ClickReviewWrite -> ProductDetailIntent.WriteReview
            is ProductDetailAction.ClickNutrients -> ProductDetailIntent.SeeNutrients
        }


    override fun collectIntent(intent: ProductDetailIntent) {
        when (intent) {
            is ProductDetailIntent.SeeNutrients -> setEffect { ProductDetailEffect.OpenNutrientDialog }
            is ProductDetailIntent.SelectReview -> setEffect { ProductDetailEffect.NavigateToReview(intent.reviewId) }
            is ProductDetailIntent.WriteReview -> setEffect { ProductDetailEffect.NavigateToReviewWrite }
            is ProductDetailIntent.SelectSimilarId -> setEffect { ProductDetailEffect.NavigateToSimilarProduct(intent.productId) }
            is ProductDetailIntent.Initialize -> setState { copy(productId = intent.id) }
        }
    }
}