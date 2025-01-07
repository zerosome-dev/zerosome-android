package com.zerosome.main.detail

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.zerosome.core.analytics.LogName
import com.zerosome.core.analytics.LogProperty
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.model.Product
import com.zerosome.domain.model.Review
import com.zerosome.feat.core.BaseViewModel
import com.zerosome.feat.core.UIAction
import com.zerosome.feat.core.UIEffect
import com.zerosome.feat.core.UIIntent
import com.zerosome.feat.core.UIState
import com.zerosome.product.GetProductDetailUseCase
import com.zerosome.review.GetReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal sealed interface ProductDetailAction : UIAction {
    data class ViewCreated(val id: Int) : ProductDetailAction

    data object ClickNutrients : ProductDetailAction

    data class ClickReview(val reviewId: Int?) : ProductDetailAction

    data object ClickReviewWrite : ProductDetailAction

    data class ClickSimilarProduct(val productId: Int) : ProductDetailAction
}

internal sealed interface ProductDetailIntent : UIIntent {
    data class Initialize(val id: Int) : ProductDetailIntent

    data class SelectSimilarId(val productId: Int) : ProductDetailIntent

    data class SelectReview(val reviewId: Int?) : ProductDetailIntent

    data object WriteReview : ProductDetailIntent

    data object SeeNutrients : ProductDetailIntent
}

internal data class ProductDetailState(
    val productId: Int? = null,
    val uiModel: List<ProductDetailUiModel> = emptyList(),
    val selectedProduct: Product? = null,
    val reviews: List<Review> = emptyList()
) : UIState

internal sealed interface ProductDetailEffect : UIEffect {
    data class NavigateToSimilarProduct(val productId: Int) : ProductDetailEffect

    data class NavigateToReview(val reviewId: Int?) : ProductDetailEffect

    data object NavigateToReviewWrite : ProductDetailEffect

    data object OpenNutrientDialog : ProductDetailEffect
}

@HiltViewModel
internal class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getReviewUseCase: GetReviewUseCase
) : BaseViewModel<ProductDetailAction, ProductDetailIntent, ProductDetailState, ProductDetailEffect>(
    initialState = ProductDetailState()
) {

    private val productFlow =
        getProductDetailUseCase().mapMerge {
            setState { copy(selectedProduct = it) }.also {
                handleUiModel()
            }
        }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = NetworkResult.Loading
            )

    private val productIdFlow =
        snapshotFlow { uiState.productId }.filterNotNull().distinctUntilChanged().onEach {
            getProductDetailUseCase += it
            getReviewUseCase += it
        }

    private val reviews = getReviewUseCase().mapMerge {
        setState { copy(reviews = it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkResult.Loading
    ).launchIn(viewModelScope)

    private fun handleUiModel() {
        val uiModelList = mutableListOf<ProductDetailUiModel>()
        uiState.selectedProduct?.let {
            uiModelList.add(
                ProductDetailUiModel.Introduction(
                    it.image,
                    it.brandName,
                    it.productName,
                    it.rating ?: 0f,
                    it.reviewCount
                )
            )
            uiModelList.add(ProductDetailUiModel.Nutrient)
            uiModelList.add(ProductDetailUiModel.Stores(it.onlineStoreList, it.offlineStoreList))
            if (it.reviewCount > 0) {
                uiModelList.add(
                    ProductDetailUiModel.Reviews(
                        it.reviewCount,
                        it.rating ?: 0f,
                        it.reviewThumbnailList
                    )
                )
            } else {
                uiModelList.add(ProductDetailUiModel.EmptyReview)
            }
            uiModelList.add(ProductDetailUiModel.SimilarProducts(it.relatedProductList))
        }
        setState {
            copy(uiModel = uiModelList)
        }
    }

    override suspend fun actionPredicate(action: ProductDetailAction): ProductDetailIntent =
        when (action) {
            is ProductDetailAction.ViewCreated -> ProductDetailIntent.Initialize(action.id).also {
                analyticsLogger.logEvent(
                    LogName.VIEW_PRODUCT_DETAIL, mapOf(
                        LogProperty.PRODUCT_ID to action.id
                    )
                )
            }

            is ProductDetailAction.ClickReview -> ProductDetailIntent.SelectReview(action.reviewId)
                .also {
                    analyticsLogger.logEvent(
                        LogName.CLICK_PRODUCT_DETAIL_REVIEW, mapOf(
                            LogProperty.PRODUCT_ID to (uiState.productId ?: 0)
                        )
                    )
                }

            is ProductDetailAction.ClickSimilarProduct -> ProductDetailIntent.SelectSimilarId(action.productId)
                .also {
                    analyticsLogger.logEvent(
                        LogName.CLICK_PRODUCT_DETAIL_RELATED_PRODUCT, mapOf(
                            LogProperty.RELATED_PRODUCT_ID to action.productId,
                            LogProperty.PRODUCT_ID to (uiState.productId ?: 0)
                        )
                    )
                }

            is ProductDetailAction.ClickReviewWrite -> ProductDetailIntent.WriteReview.also {
                analyticsLogger.logEvent(
                    LogName.CLICK_PRODUCT_DETAIL_REVIEW_WRITE, mapOf(
                        LogProperty.PRODUCT_ID to (uiState.productId ?: 0)
                    )
                )
            }

            is ProductDetailAction.ClickNutrients -> ProductDetailIntent.SeeNutrients.also {
                analyticsLogger.logEvent(
                    LogName.CLICK_PRODUCT_DETAIL_SHOW_NUTRIENT, mapOf(
                        LogProperty.PRODUCT_ID to (uiState.productId ?: 0)
                    )
                )
            }
        }


    override suspend fun collectIntent(intent: ProductDetailIntent) {
        when (intent) {
            is ProductDetailIntent.SeeNutrients -> setEffect { ProductDetailEffect.OpenNutrientDialog }
            is ProductDetailIntent.SelectReview -> setEffect {
                ProductDetailEffect.NavigateToReview(
                    intent.reviewId
                )
            }

            is ProductDetailIntent.WriteReview -> setEffect { ProductDetailEffect.NavigateToReviewWrite }
            is ProductDetailIntent.SelectSimilarId -> setEffect {
                ProductDetailEffect.NavigateToSimilarProduct(
                    intent.productId
                )
            }

            is ProductDetailIntent.Initialize -> getProductDetailUseCase += intent.id
        }
    }
}