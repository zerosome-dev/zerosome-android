package com.zerosome.main.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.R
import com.zerosome.design.ui.component.ButtonSize
import com.zerosome.design.ui.component.ButtonType
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSImage
import com.zerosome.design.ui.theme.Body3
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.NonLazyVerticalGrid
import com.zerosome.domain.model.CategoryDepth2

@Composable
internal fun CategorySelectionScreen(
    onCategorySelected: (depth1: String, depth2: String?) -> Unit,
    viewModel: CategorySelectionViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    val effect by viewModel.uiEffect.collectAsState(initial = null)
    LaunchedEffect(key1 = effect) {
        when(effect) {
            is CategorySelectionEffect.NavigateToCategorySelectionDetail -> {
                onCategorySelected((effect as CategorySelectionEffect.NavigateToCategorySelectionDetail).depth1Id, (effect as CategorySelectionEffect.NavigateToCategorySelectionDetail).depth2Id)
            }
            else -> {
                viewModel.clearError()
            }
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        Column {
            Row(modifier = Modifier.padding(horizontal = 22.dp, vertical = 10.dp)) {
                Text(text = "카테고리", style = H1)
            }
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.categories) {
                    GridItemSpan(
                        categoryName = it.categoryName,
                        categoryItems = it.categoryDepth2,
                        onClickCategory = { depth2 ->
                            viewModel.setAction(
                                CategorySelectionAction.ClickSpecificCategory(
                                    it,
                                    depth2
                                )
                            )
                        },
                        onClickCategoryAll = {
                            viewModel.setAction(
                                CategorySelectionAction.ClickCategoryMore(
                                    it
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun GridItemSpan(
    categoryName: String,
    categoryItems: List<CategoryDepth2>,
    onClickCategory: (depth2: CategoryDepth2) -> Unit,
    onClickCategoryAll: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 12.dp)
                .padding(horizontal = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryName, style = H2, modifier = Modifier
                    .weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                modifier = Modifier.size(16.dp),
                contentDescription = "MORE"
            )
        }
        NonLazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 22.dp),
            columns = 4,
            itemCount = if (categoryItems.size > 8) 8 else categoryItems.size,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    role = Role.Button,
                    onClick = { onClickCategory(categoryItems[it]) }
                )
            ) {
                ZSImage(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(color = ZSColor.Neutral50, shape = RoundedCornerShape(8)),
                    painter = painterResource(id = requireNotNull(categoryItems[it].categoryPainterResId))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = categoryItems[it].categoryName, style = Body3, textAlign = TextAlign.Center)
            }
        }
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(ZSColor.Neutral50)
            )
        }
        if (categoryItems.size > 8) {
            ZSButton(
                onClick = onClickCategoryAll,
                buttonType = ButtonType.SECONDARY,
                buttonSize = ButtonSize.SMALL
            ) {
                Text(text = "전체 보기", style = Body3, color = ZSColor.Neutral900)
            }
        }
    }
}
