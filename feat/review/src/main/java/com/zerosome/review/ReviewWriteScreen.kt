package com.zerosome.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.R
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSImage
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.component.ZSTextField
import com.zerosome.design.ui.theme.Body4
import com.zerosome.design.ui.theme.Label1
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.StarRatingView

@Composable
fun ReviewWriteScreen(
    onBackPressed: () -> Unit
) {
    val viewModel: ReviewWriteViewModel = hiltViewModel()
    val effect by viewModel.uiEffect.collectAsState(initial = null)
    LaunchedEffect(key1 = effect) {
        when (effect) {
            is ReviewWriteEffect.PopToBackStack -> onBackPressed()
            else -> {}
        }
    }
    ZSScreen(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        onInputEnabled = true
    ) {
        ZSAppBar(
            navTitle = "리뷰 작성",
            backNavigationIcon = painterResource(id = R.drawable.ic_chevron_left),
            onBackPressed = onBackPressed
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ZSImage(
                    imageString = viewModel.uiState.selectedProduct?.image ?: "",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .drawBehind {
                            drawRoundRect(
                                color = ZSColor.Neutral50,
                                cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                            )
                        },
                    scale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "[${viewModel.uiState.selectedProduct?.brandName}]",
                    style = Label1,
                    color = ZSColor.Neutral500
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = viewModel.uiState.selectedProduct?.productName ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = SubTitle1,
                    color = ZSColor.Neutral900
                )
                Spacer(modifier = Modifier.height(30.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "상품은 어떠셨나요?", style = SubTitle1, color = ZSColor.Neutral700)
                Spacer(modifier = Modifier.height(20.dp))
                StarRatingView(
                    modifier = Modifier.fillMaxWidth(),
                    rating = viewModel.uiState.reviewScore,
                    onRatingChanged = {
                        viewModel.setAction(ReviewWriteAction.ClickReviewScore(it))
                    })
                Spacer(modifier = Modifier.height(30.dp))
                ZSTextField(
                    text = viewModel.uiState.reviewText,
                    onTextChanged = { viewModel.setAction(ReviewWriteAction.WriteReview(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    singleLine = false,
                    minLines = 3,
                )
                Text(
                    text = "${viewModel.uiState.reviewText.length}/1000",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 22.dp),
                    style = Body4,
                    color = ZSColor.Neutral400
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
            ZSButton(
                onClick = { viewModel.setAction(ReviewWriteAction.ClickConfirmButton) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "작성 완료")
            }
        }
    }
}