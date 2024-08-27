package com.zerosome.main.category

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.zerosome.core.BaseViewModel
import com.zerosome.core.UIAction
import com.zerosome.core.UIEffect
import com.zerosome.core.UIIntent
import com.zerosome.core.UIState
import com.zerosome.domain.category.GetCategoriesUseCase
import com.zerosome.domain.category.GetLowerCategoryUseCase
import com.zerosome.domain.model.Brand
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.model.CategoryProduct
import com.zerosome.domain.model.SortItem
import com.zerosome.domain.model.ZeroCategory
import com.zerosome.product.GetBrandsUseCase
import com.zerosome.product.GetFilterUseCase
import com.zerosome.product.GetProductsByFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal sealed interface CategoryDetailAction : UIAction {
    data class ViewCreated(val category1Id: String, val category2id: String?) : CategoryDetailAction

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

    data object ClickConfirm : CategoryDetailAction
}

internal sealed interface CategoryDetailIntent : UIIntent {
    data class Initialize(val category1Id: String, val category2Id: String?) : CategoryDetailIntent

    data class OpenDialog(val type: DialogType) : CategoryDetailIntent

    data class SelectCategory(val category: CategoryDepth2?) : CategoryDetailIntent

    data class SelectSortType(val sortItem: SortItem) : CategoryDetailIntent

    data class AddBrand(val brand: String) : CategoryDetailIntent

    data class DeleteBrand(val brand: String) : CategoryDetailIntent

    data class AddTag(val tagName: String) : CategoryDetailIntent

    data class DeleteTag(val tagName: String) : CategoryDetailIntent

    data class Clear(val type: DialogType) : CategoryDetailIntent

    data object ClearEffect : CategoryDetailIntent

    data object ChangeConfirm : CategoryDetailIntent
}

internal data class CategoryDetailState(
    val depth1CategoryName: String? = null,
    val depth2CategoryName: String? = null,
    val depth1Category: CategoryDepth1? = null,
    val depth2Category: CategoryDepth2? = null,
    val categoryList: List<CategoryDepth2> = emptyList(),
    val sort: SortItem = SortItem.RECENT,
    val brands: List<Brand> = emptyList(),
    val selectedBrands: List<Brand> = emptyList(),
    val zeroTag: List<ZeroCategory> = emptyList(),
    val selectedTags: List<ZeroCategory> = emptyList(),
    val productList: List<CategoryProduct> = emptyList(),
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
    private val categoryDetailFilterUseCase: GetProductsByFilterUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getLowerCategoryUseCase: GetLowerCategoryUseCase,
    getBrandsUseCase: GetBrandsUseCase,
    getTagUseCase: GetFilterUseCase,
) : BaseViewModel<CategoryDetailAction, CategoryDetailIntent, CategoryDetailState, CategoryDetailEffect>(
    initialState = CategoryDetailState()
) {

    private val productFlow = snapshotFlow { uiState.depth1CategoryName }.filterNotNull()
        .flatMapLatest {
            getCategoriesUseCase.getSpecificCategory(it)
        }.onEach {
            setState { copy(depth1Category = it) }
        }.filterNotNull()
        .flatMapLatest {
            getLowerCategoryUseCase(it.categoryCode)
        }.mapMerge()
        .filterNotNull().onEach {
            setState { copy(categoryList = it) }
        }.flatMapLatest {
            val category =
                it.find { category -> category.categoryCode == uiState.depth2CategoryName }?.categoryCode
                    ?: it.first().categoryCode
            categoryDetailFilterUseCase(category)
        }.mapMerge().onEach {
            setState { copy(productList = it ?: emptyList()) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        ).launchIn(viewModelScope)

    private val filterFlows = combine(
        getBrandsUseCase().mapMerge().filterNotNull(),
        getTagUseCase().mapMerge().filterNotNull()
    ) { brands, tags ->
        setState {
            copy(
                brands = brands,
                zeroTag = tags
            )
        }
    }.launchIn(viewModelScope)

    override fun actionPredicate(action: CategoryDetailAction): CategoryDetailIntent {
        return when (action) {
            is CategoryDetailAction.ViewCreated -> CategoryDetailIntent.Initialize(
                action.category1Id,
                action.category2id
            )

            CategoryDetailAction.ClearCategory -> CategoryDetailIntent.Clear(DialogType.DEPTH2)
            CategoryDetailAction.ClickOpenCategoryDepth2 -> CategoryDetailIntent.OpenDialog(
                DialogType.DEPTH2
            )

            CategoryDetailAction.ClearTag -> CategoryDetailIntent.Clear(DialogType.TAG)
            CategoryDetailAction.ClearBrand -> CategoryDetailIntent.Clear(DialogType.BRAND)
            CategoryDetailAction.ClickOpenTag -> CategoryDetailIntent.OpenDialog(DialogType.TAG)
            is CategoryDetailAction.ClickSelectTag -> if (uiState.selectedTags.find { it.categoryName == action.tagName } != null) {
                CategoryDetailIntent.DeleteTag(action.tagName)
            } else {
                CategoryDetailIntent.AddTag(action.tagName)
            }

            is CategoryDetailAction.ClickSelectBrand -> if (uiState.selectedBrands.find { it.brandName == action.brandName } != null) {
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
            is CategoryDetailIntent.Initialize -> setState {
                copy(
                    depth1CategoryName = intent.category1Id,
                    depth2CategoryName = intent.category2Id
                )
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
                brands.find { it.brandName == intent.brand }?.let {
                    copy(
                        selectedBrands = selectedBrands.toMutableList().apply { add(it) })
                } ?: this
            }

            is CategoryDetailIntent.DeleteBrand -> setState {
                brands.find { it.brandName == intent.brand }?.let {
                    copy(
                        selectedBrands = selectedBrands.toMutableList().apply { remove(it) })
                } ?: this
            }

            is CategoryDetailIntent.AddTag -> setState {
                zeroTag.find { it.categoryName == intent.tagName }?.let {
                    copy(
                        selectedTags = selectedTags.toMutableList().apply { add(it) })
                } ?: this

            }

            is CategoryDetailIntent.DeleteTag -> setState {
                zeroTag.find { it.categoryName == intent.tagName }?.let {
                    copy(
                        selectedTags = selectedTags.toMutableList().apply { remove(it) })
                } ?: this
            }

            is CategoryDetailIntent.Clear -> {
                when (intent.type) {
                    DialogType.DEPTH2 -> setState { copy(depth2Category = null) }
                    DialogType.BRAND -> setState { copy(selectedBrands = emptyList()) }
                    DialogType.TAG -> setState { copy(selectedTags = emptyList()) }
                    else -> {}
                }
            }

            is CategoryDetailIntent.ClearEffect -> setEffect { CategoryDetailEffect.Idle }
            is CategoryDetailIntent.ChangeConfirm -> getChangedData()
        }
    }

    private fun getChangedData() = withState {
        categoryDetailFilterUseCase.setFilteringData(
            category2Code = depth2Category?.categoryCode,
            sortItem = sort,
            brandList = selectedBrands.map { it.brandCode },
            zeroTagList = selectedTags.map { it.categoryCode }
        )
    }
}

internal enum class DialogType {
    SORT, DEPTH2, BRAND, TAG
}