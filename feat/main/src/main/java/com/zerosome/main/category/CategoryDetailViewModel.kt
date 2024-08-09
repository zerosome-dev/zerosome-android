package com.zerosome.main.category

import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.model.SortItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

internal sealed interface CategoryDetailAction : UIAction {
    data class ViewCreated(val category1Id: String, val category2id: String?): CategoryDetailAction

    data object ClickOpenSort : CategoryDetailAction

    data class ClickSelectSort(val sortItem: SortItem) : CategoryDetailAction

    data object ClickOpenCategoryDepth2 : CategoryDetailAction

    data class ClickSelectCategoryDepth2(val category: CategoryDepth2?) : CategoryDetailAction

    data object ClickOpenBrand : CategoryDetailAction

    data class ClickSelectBrand(val brandName: String) : CategoryDetailAction

    data object ClickOpenTag : CategoryDetailAction

    data class ClickSelectTag(val tagName: String) : CategoryDetailAction

    data object ClearBrand : CategoryDetailAction

    data object ClearTag : CategoryDetailAction

    data object ClearCategory : CategoryDetailAction

    data object DismissDialog : CategoryDetailAction

    data object ClickConfirm: CategoryDetailAction
}

internal sealed interface CategoryDetailIntent : UIIntent {
    data class Initialize(val category1Id: String, val category2id: String?) : CategoryDetailIntent

    data class OpenDialog(val type: DialogType) : CategoryDetailIntent

    data class SelectCategory(val category: CategoryDepth2?) : CategoryDetailIntent

    data class SelectSortType(val sortItem: SortItem) : CategoryDetailIntent

    data class AddBrand(val brand: String) : CategoryDetailIntent

    data class DeleteBrand(val brand: String) : CategoryDetailIntent

    data class AddTag(val tagName: String) : CategoryDetailIntent

    data class DeleteTag(val tagName: String) : CategoryDetailIntent

    data class Clear(val type: DialogType) : CategoryDetailIntent

    data object ClearEffect : CategoryDetailIntent

    data object ChangeConfirm: CategoryDetailIntent
}

internal data class CategoryDetailState(
    val depth2Category: CategoryDepth2? = null,
    val categoryList: List<CategoryDepth2> = emptyList(),
    val sort: SortItem = SortItem.LATEST,
    val brands: List<String> = emptyList(),
    val selectedBrands: List<String> = emptyList(),
    val zeroTag: List<String> = emptyList(),
    val selectedTags: List<String> = emptyList(),
) : UIState

internal sealed interface CategoryDetailEffect : UIEffect {
    data object OpenSortDialog : CategoryDetailEffect
    data object OpenBrandDialog : CategoryDetailEffect
    data object OpenCategoryDialog : CategoryDetailEffect
    data object OpenTagDialog : CategoryDetailEffect
    data object Idle : CategoryDetailEffect
}

@HiltViewModel
internal class CategoryDetailViewModel @Inject constructor(

) : BaseViewModel<CategoryDetailAction, CategoryDetailIntent, CategoryDetailState, CategoryDetailEffect>(
        initialState = CategoryDetailState()
    ) {

    override fun actionPredicate(action: CategoryDetailAction): CategoryDetailIntent {
        return when (action) {
            is CategoryDetailAction.ViewCreated -> CategoryDetailIntent.Initialize(action.category1Id, action.category2id)
            CategoryDetailAction.ClearCategory -> CategoryDetailIntent.Clear(DialogType.DEPTH2)
            CategoryDetailAction.ClickOpenCategoryDepth2 -> CategoryDetailIntent.OpenDialog(
                DialogType.DEPTH2
            )

            CategoryDetailAction.ClearTag -> CategoryDetailIntent.Clear(DialogType.TAG)
            CategoryDetailAction.ClearBrand -> CategoryDetailIntent.Clear(DialogType.BRAND)
            CategoryDetailAction.ClickOpenTag -> CategoryDetailIntent.OpenDialog(DialogType.TAG)
            is CategoryDetailAction.ClickSelectTag -> if (uiState.selectedTags.contains(action.tagName)) {
                CategoryDetailIntent.DeleteTag(action.tagName)
            } else {
                CategoryDetailIntent.AddTag(action.tagName)
            }

            is CategoryDetailAction.ClickSelectBrand -> if (uiState.selectedBrands.contains(action.brandName)) {
                CategoryDetailIntent.DeleteBrand(action.brandName)
            } else {
                CategoryDetailIntent.AddBrand(action.brandName)
            }

            CategoryDetailAction.ClickOpenBrand -> CategoryDetailIntent.OpenDialog(DialogType.BRAND)
            is CategoryDetailAction.ClickSelectCategoryDepth2 -> CategoryDetailIntent.SelectCategory(
                action.category
            )

            CategoryDetailAction.ClickOpenSort -> CategoryDetailIntent.OpenDialog(DialogType.SORT)
            is CategoryDetailAction.ClickSelectSort -> CategoryDetailIntent.SelectSortType(action.sortItem)
            CategoryDetailAction.DismissDialog -> CategoryDetailIntent.ClearEffect
            CategoryDetailAction.ClickConfirm -> CategoryDetailIntent.ChangeConfirm
        }
    }

    override fun collectIntent(intent: CategoryDetailIntent) {
        when (intent) {
            is CategoryDetailIntent.Initialize -> {
                getChangedData()
            }
            is CategoryDetailIntent.OpenDialog -> setEffect {
                when (intent.type) {
                    DialogType.DEPTH2 -> CategoryDetailEffect.OpenCategoryDialog
                    DialogType.SORT -> CategoryDetailEffect.OpenSortDialog
                    DialogType.BRAND -> CategoryDetailEffect.OpenBrandDialog
                    DialogType.TAG -> CategoryDetailEffect.OpenTagDialog
                }
            }

            is CategoryDetailIntent.SelectCategory -> setState { copy(depth2Category = intent.category) }
            is CategoryDetailIntent.SelectSortType -> setState { copy(sort = intent.sortItem) }.also { setEffect { CategoryDetailEffect.Idle } }
            is CategoryDetailIntent.AddBrand -> setState {
                copy(
                    brands = selectedBrands.toMutableList().apply { add(intent.brand) })
            }

            is CategoryDetailIntent.DeleteBrand -> setState {
                copy(
                    brands = selectedBrands.toMutableList().apply { remove(intent.brand) })
            }

            is CategoryDetailIntent.AddTag -> setState {
                copy(
                    zeroTag = selectedTags.toMutableList().apply { add(intent.tagName) })
            }

            is CategoryDetailIntent.DeleteTag -> setState {
                copy(
                    zeroTag = selectedTags.toMutableList().apply { remove(intent.tagName) })
            }

            is CategoryDetailIntent.Clear -> {
                when (intent.type) {
                    DialogType.DEPTH2 -> setState { copy(depth2Category = null) }
                    DialogType.BRAND -> setState { copy(brands = emptyList()) }
                    DialogType.TAG -> setState { copy(zeroTag = emptyList()) }
                    else -> {}
                }
            }

            is CategoryDetailIntent.ClearEffect -> setEffect { CategoryDetailEffect.Idle }
            is CategoryDetailIntent.ChangeConfirm -> getChangedData()
        }
    }

    private fun getChangedData() = withState {

    }
}

internal enum class DialogType {
    SORT, DEPTH2, BRAND, TAG
}