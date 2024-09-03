package com.zerosome.main.category

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.category.GetCategoriesUseCase
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed interface CategorySelectionAction: UIAction {
    data class ClickCategoryMore(val depth1: CategoryDepth1): CategorySelectionAction

    data class ClickSpecificCategory(val depth1: CategoryDepth1, val depth2: CategoryDepth2): CategorySelectionAction
}

internal sealed interface CategorySelectionIntent: UIIntent {
    data class SelectMore(val depth1: CategoryDepth1, val depth2: CategoryDepth2? = null): CategorySelectionIntent
}

internal data class CategorySelectionState(
    val categories: List<CategoryDepth1> = emptyList()
): UIState

internal sealed interface CategorySelectionEffect: UIEffect {
    data class NavigateToCategorySelectionDetail(val depth1Id: String, val depth2Id: String? = null): CategorySelectionEffect

}

@HiltViewModel
internal class CategorySelectionViewModel @Inject constructor(
    private val allCategoriesUseCase: GetCategoriesUseCase,
): BaseViewModel<CategorySelectionAction, CategorySelectionIntent, CategorySelectionState, CategorySelectionEffect>(
    initialState = CategorySelectionState()
) {

    init {
        viewModelScope.launch {
            allCategoriesUseCase().mapMerge().onEach {
                setState { copy(categories = it ?: emptyList()) }
            }.collect()
        }
    }

    override fun actionPredicate(action: CategorySelectionAction): CategorySelectionIntent =
        when(action) {
            is CategorySelectionAction.ClickCategoryMore -> CategorySelectionIntent.SelectMore(action.depth1, null)
            is CategorySelectionAction.ClickSpecificCategory -> CategorySelectionIntent.SelectMore(action.depth1, action.depth2)
        }

    override fun collectIntent(intent: CategorySelectionIntent) {
        when(intent) {
            is CategorySelectionIntent.SelectMore -> setEffect {
                CategorySelectionEffect.NavigateToCategorySelectionDetail(intent.depth1.categoryCode, intent.depth2?.categoryCode)
            }
        }
    }
}