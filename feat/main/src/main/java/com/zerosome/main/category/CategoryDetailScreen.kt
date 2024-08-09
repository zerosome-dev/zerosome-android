package com.zerosome.main.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.R
import com.zerosome.design.ui.component.ButtonSize
import com.zerosome.design.ui.component.ButtonType
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSChip
import com.zerosome.design.ui.component.ZSDropdown
import com.zerosome.design.ui.theme.Body3
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.SubTitle2
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.CategoryComponent
import com.zerosome.design.ui.view.NonLazyVerticalGrid
import com.zerosome.design.ui.view.SimpleCardComponent
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.model.SortItem

@Composable
internal fun CategoryDetailScreen(
    category1Id: String,
    category2Id: String? = null,
    onBackPressed: () -> Unit,
    viewModel: CategoryDetailViewModel = hiltViewModel(),
) {
    val effect by viewModel.uiEffect.collectAsState(initial = null)
    when (effect) {
        is CategoryDetailEffect.OpenSortDialog -> {
            CategorySortBottomSheet(
                onItemSet = {
                    viewModel.setAction(CategoryDetailAction.ClickSelectSort(it))
                }
            )
        }

        is CategoryDetailEffect.OpenBrandDialog -> {
            BrandFilterBottomSheet(
                brandList = viewModel.uiState.brands,
                selectedBrandList = viewModel.uiState.selectedBrands,
                onSelect = { viewModel.setAction(CategoryDetailAction.ClickSelectBrand(it)) },
                onClear = { viewModel.setAction(CategoryDetailAction.ClearBrand) },
                onDismissRequest = {
                    viewModel.setAction(CategoryDetailAction.DismissDialog)
                }
            )
        }

        is CategoryDetailEffect.OpenTagDialog -> {
            TagFilter(
                tagList = viewModel.uiState.zeroTag,
                selectedTagList = viewModel.uiState.selectedTags,
                onSelect = { viewModel.setAction(CategoryDetailAction.ClickSelectTag(it)) },
                onClear = { viewModel.setAction(CategoryDetailAction.ClearTag) },
                onDismissRequest = { viewModel.setAction(CategoryDetailAction.DismissDialog) })
        }

        is CategoryDetailEffect.OpenCategoryDialog -> {
            CategoryFilterBottomSheet(
                selectedCategoryName = "생수/음료",
                onSelect = { viewModel.setAction(CategoryDetailAction.ClickSelectCategoryDepth2(it)) },
                onClear = { viewModel.setAction(CategoryDetailAction.ClearCategory) },
                onDismissRequest = { viewModel.setAction(CategoryDetailAction.DismissDialog) })
        }

        else -> {}
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ZSAppBar(
            onBackPressed = onBackPressed,
            backNavigationIcon = painterResource(id = R.drawable.ic_chevron_left),
            navTitle = "생수/음료"
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(vertical = 10.dp, horizontal = 22.dp)),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                ZSDropdown(
                    onItemSelected = { viewModel.setAction(CategoryDetailAction.ClickOpenCategoryDepth2) },
                    placeholderText = "카테고리 선택"
                )
            }
            item {
                ZSDropdown(
                    onItemSelected = { viewModel.setAction(CategoryDetailAction.ClickOpenBrand) },
                    placeholderText = "브랜드"
                )
            }
            item {
                ZSDropdown(
                    onItemSelected = { viewModel.setAction(CategoryDetailAction.ClickOpenTag) },
                    placeholderText = "제로태그"
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(thickness = 1.dp, color = ZSColor.Neutral100)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "32개의 상품",
                style = Body3,
                color = ZSColor.Neutral900,
                modifier = Modifier.weight(1f)
            )
            ZSButton(
                onClick = { viewModel.setAction(CategoryDetailAction.ClickOpenSort) },
                buttonType = ButtonType.TEXT,
                buttonSize = ButtonSize.NONE
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "최신 등록순", color = ZSColor.Neutral900, style = Body3)
                    Spacer(modifier = Modifier.width(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = "DROPDOWN_DOWN"
                    )
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(40) {
                SimpleCardComponent(
                    title = "아이템 $it",
                    brandName = "브랜드 $it",
                    image = "",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {})
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySortBottomSheet(
    selectedSortItem: SortItem = SortItem.LATEST,
    onItemSet: (item: SortItem) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onItemSet(selectedSortItem) },
        containerColor = Color.White
    ) {
        SortItem.entries.forEach {
            Row(
                modifier = Modifier
                    .padding(vertical = 14.dp, horizontal = 24.dp)
                    .clickable {
                        onItemSet(it)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = it.sortName, style = SubTitle2, color = ZSColor.Neutral900)
                if (selectedSortItem == it) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = "CHECK"
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BrandFilterBottomSheet(
    brandList: List<String>,
    selectedBrandList: List<String>,
    onSelect: (String) -> Unit,
    onClear: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest, containerColor = Color.White) {
        Text(text = "브랜드", style = H2, modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(modifier = Modifier.height(20.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            brandList.forEach {
                ZSChip(
                    enable = selectedBrandList.contains(it),
                    chipText = it,
                    onClick = { onSelect(it) })
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ZSButton(
                onClick = onClear,
                buttonType = ButtonType.SECONDARY,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "초기화",
                    style = SubTitle1,
                    color = ZSColor.Neutral600,
                )
            }
            ZSButton(onClick = onDismissRequest, modifier = Modifier.weight(2f)) {
                Text(
                    text = "적용",
                    style = SubTitle1,
                    color = Color.White,)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterBottomSheet(
    selectedCategoryName: String,
    categories: List<CategoryDepth2> = emptyList(),
    selectedCategory: CategoryDepth2? = null,
    onSelect: (CategoryDepth2?) -> Unit,
    onClear: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest, containerColor = Color.White) {
        Text(
            text = selectedCategoryName,
            style = H2,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        NonLazyVerticalGrid(
            columns = 4,
            itemCount = categories.size,
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            CategoryComponent(
                isSelected = selectedCategory == categories[it],
                cardImage = categories[it].categoryImage ?: "",
                cardTitle = categories[it].categoryName,
                onCategoryClick = { onSelect(categories.find { name -> name.categoryName == it }) }
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ZSButton(
                onClick = onClear,
                buttonType = ButtonType.SECONDARY,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "초기화",
                    style = SubTitle1,
                    color = ZSColor.Neutral600,

                    )
            }
            ZSButton(onClick = onDismissRequest, modifier = Modifier.weight(2f)) {
                Text(
                    text = "적용",
                    style = SubTitle1,
                    color = Color.White,

                    )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TagFilter(
    tagList: List<String>,
    selectedTagList: List<String> = emptyList(),
    onSelect: (String) -> Unit,
    onClear: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest, containerColor = Color.White) {
        Text(text = "제로태그", style = H2, modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(modifier = Modifier.height(20.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            tagList.forEach {
                ZSChip(
                    enable = selectedTagList.contains(it),
                    chipText = it,
                    onClick = { onSelect(it) })
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ZSButton(
                onClick = onClear,
                buttonType = ButtonType.SECONDARY,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "초기화",
                    style = SubTitle1,
                    color = ZSColor.Neutral600,
                )
            }
            ZSButton(onClick = onDismissRequest, modifier = Modifier.weight(2f)) {
                Text(
                    text = "적용",
                    style = SubTitle1,
                    color = Color.White,
                )
            }
        }

    }
}