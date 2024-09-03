package com.zerosome.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.extension.ChangeSystemColor
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.theme.SubTitle1

@Composable
internal fun ReviewScreen(
    onBackPressed: () -> Unit,
    onReviewWrite: () -> Unit,
    onReviewReport: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val effect by viewModel.uiEffect.collectAsState(initial = null)
    ChangeSystemColor(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent
    )
    LaunchedEffect(key1 = viewModel.uiEffect) {
        when (effect) {
            is ReviewEffect.MoveToWriteReview -> onReviewWrite()
            else -> {}
        }
    }
    ZSScreen(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        isLoading = viewModel.isLoading
    ) {
        ZSAppBar(painterResource(com.zerosome.design.R.drawable.ic_chevron_left), onBackPressed = {
            onBackPressed()
        }, navTitle = "상품 리뷰")
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                itemsIndexed(
                    viewModel.uiState.reviewList) { index, review ->
                    ReviewListComponent(
                        authorName = review.authorNickname,
                        dateString = review.regDate,
                        rating = review.rating,
                        reviewString = review.reviewContents,
                        index == viewModel.uiState.reviewList.lastIndex,
                        onReviewReport
                    )
                }
            }

            ZSButton(modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
                .align(Alignment.BottomCenter), onClick = {
                    viewModel.setAction(ReviewAction.ClickWriteReview)
            }) {
                Text("리뷰 작성", style = SubTitle1, color = Color.White)
            }
        }
    }

}