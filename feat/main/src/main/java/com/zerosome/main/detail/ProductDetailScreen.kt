package com.zerosome.main.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.R
import com.zerosome.design.ui.component.ButtonSize
import com.zerosome.design.ui.component.ButtonType
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSImage
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.component.ZSTag
import com.zerosome.design.ui.component.ZSTagType
import com.zerosome.design.ui.theme.Body1
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.Body3
import com.zerosome.design.ui.theme.Body4
import com.zerosome.design.ui.theme.Caption
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.SimpleCardComponent
import com.zerosome.domain.model.Nutrient
import com.zerosome.domain.model.RelatedProduct
import com.zerosome.domain.model.ReviewThumbnail
import com.zerosome.domain.model.Store
import com.zerosome.review.ReviewRatingComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductDetailScreen(
    productId: Int,
    onBackPressed: () -> Unit,
    onClickReview: (reviewId: Int?, productId: Int) -> Unit,
    onClickWriteReview: (productId: Int) -> Unit,
    onClickSimilarProduct: (productId: Int) -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val effect by viewModel.uiEffect.collectAsState(initial = null)
    var dialogShown by remember {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState()
    LaunchedEffect(key1 = productId) {
        viewModel.setAction(ProductDetailAction.ViewCreated(productId))
    }
    LaunchedEffect(key1 = effect) {
        when (effect) {
            is ProductDetailEffect.NavigateToSimilarProduct -> onClickSimilarProduct((effect as ProductDetailEffect.NavigateToSimilarProduct).productId)
            is ProductDetailEffect.NavigateToReview -> onClickReview(
                (effect as ProductDetailEffect.NavigateToReview).reviewId,
                requireNotNull(viewModel.uiState.productId)
            )

            is ProductDetailEffect.NavigateToReviewWrite -> onClickWriteReview(
                requireNotNull(
                    viewModel.uiState.productId
                )
            )

            is ProductDetailEffect.OpenNutrientDialog -> dialogShown = true
            else -> {}
        }
    }
    ZSScreen(
        isLoading = viewModel.isLoading,
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .drawBehind {
                drawRect(ZSColor.Neutral50)
            }
    ) {
        ZSAppBar(
            navTitle = viewModel.uiState.selectedProduct?.productName ?: "",
            backNavigationIcon = painterResource(
                id = R.drawable.ic_chevron_left
            ),
            onBackPressed = onBackPressed
        )
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 50.dp)
            ) {
                items(viewModel.uiState.uiModel) { model ->
                    when (model) {
                        is ProductDetailUiModel.Introduction -> ItemDetailComponent(
                            profileImage = model.imageSrc,
                            brandName = model.brandName,
                            productName = model.name,
                            rating = model.reviewRating,
                            reviewCount = model.reviewCount
                        )

                        is ProductDetailUiModel.Nutrient -> NutrientComponent {
                            viewModel.setAction(ProductDetailAction.ClickNutrients)
                        }

                        is ProductDetailUiModel.Stores -> SellerComponent(
                            offline = model.offlineStore,
                            online = model.onlineStore
                        ) {

                        }

                        is ProductDetailUiModel.EmptyReview -> EmptyReviewComponent {
                            viewModel.setAction(ProductDetailAction.ClickReviewWrite)
                        }

                        is ProductDetailUiModel.Reviews -> ReviewComponent(
                            reviewCount = model.reviewCount,
                            totalRating = model.rating,
                            reviewThumbnails = model.reviewList
                        ) {
                            viewModel.setAction(ProductDetailAction.ClickReview(it))
                        }

                        is ProductDetailUiModel.SimilarProducts -> SimilarComponent(relatedProduct = model.products) {
                            viewModel.setAction(ProductDetailAction.ClickSimilarProduct(it))
                        }
                    }
                }
            }
            ZSButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(bottom = 10.dp)
                    .align(Alignment.BottomCenter), onClick = {
                    viewModel.setAction(ProductDetailAction.ClickReviewWrite)
                }
            ) {
                Text(text = "리뷰 작성")
            }
        }
    }
    if (dialogShown) {
        NutrientBottomDialog(
            bottomSheetState,
            onDismiss = {
                viewModel.clearEffect().also {
                    dialogShown = false
                }
            }, nutrientList = viewModel.uiState.selectedProduct?.nutrientList ?: emptyList()
        )
    }
}

@Composable
fun NutrientComponent(onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .drawBehind {
            drawRect(color = Color.White)
        }
        .clickable(onClick = onClick)) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 14.dp)
        ) {
            Text(text = "제품 영양 정보", style = H2, color = ZSColor.Neutral900)
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun ItemDetailComponent(
    profileImage: String,
    brandName: String,
    productName: String,
    rating: Float,
    reviewCount: Int,
) {
    ZSImage(
        imageString = profileImage,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .drawBehind {
                drawRect(Color.White)
            }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRect(Color.White)
            }
            .padding(horizontal = 22.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "[$brandName]", style = Body2, color = ZSColor.Neutral500)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = productName, style = SubTitle1, color = ZSColor.Neutral900)
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = ZSColor.Neutral100
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            ReviewRatingComponent(rating = rating)
            Spacer(modifier = Modifier.width(6.dp))
            VerticalDivider()
            Text(text = "${reviewCount}개 리뷰", style = Body2, color = ZSColor.Neutral500)
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutrientBottomDialog(
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    nutrientList: List<Nutrient>
) {
    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismiss,
        dragHandle = {},
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "제품 영양 정보", style = H2, color = ZSColor.Neutral900)
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "CLOSE",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDismiss() })
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 14.dp),
            ) {
                items(nutrientList) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = it.nutrientName, style = Body2)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "(${it.amount})${it.amountStandard} ((${it.percentage})${it.serviceStandard})")
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(thickness = 1.dp, color = ZSColor.Neutral100)
                }
            }
        }
    }

}

@Composable
fun EmptyReviewComponent(onClickWriteReview: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .drawBehind {
            drawRect(color = Color.White)
        }) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "아직 리뷰가 없어요",
            style = H2,
            color = ZSColor.Neutral900,
            modifier = Modifier.padding(start = 22.dp)
        )
        Spacer(modifier = Modifier.height(62.dp))
        Text(
            text = "첫 리뷰를 작성해보세요!",
            style = H2,
            color = ZSColor.Neutral900,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        ZSButton(
            onClick = onClickWriteReview,
            buttonType = ButtonType.SECONDARY,
            buttonSize = ButtonSize.MEDIUM,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "리뷰 작성하러 가기", style = Body1, color = ZSColor.Neutral900)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SellerComponent(
    offline: List<Store.Offline>,
    online: List<Store.Online>,
    onCLickOnlineStore: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRect(Color.White)
            }
            .padding(24.dp)
    ) {
        Text(text = "오프라인 판매처", style = H1, color = ZSColor.Neutral900)
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            offline.forEach {
                ZSTag(title = it.storeName, type = ZSTagType.OUTLINE)
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "온라인 판매처", style = H1, color = ZSColor.Neutral900)
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            online.forEach {
                OnlineSellerComponent(
                    sellerName = it.storeName,
                    onClickONLineStore = onCLickOnlineStore
                )
            }
        }
    }
}

@Composable
private fun OnlineSellerComponent(
    sellerName: String,
    onClickONLineStore: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRoundRect(
                    ZSColor.Neutral50,
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                )
            }
            .clickable { onClickONLineStore(sellerName) }
    ) {
        Text(
            text = sellerName, modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 16.dp),
            style = Body2,
            color = ZSColor.Neutral600
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "바로가기", style = Body2, color = ZSColor.Neutral400, modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(end = 16.dp)
        )
    }
}

@Composable
private fun ReviewComponent(
    reviewCount: Int,
    totalRating: Float,
    reviewThumbnails: List<ReviewThumbnail>,
    onClickReview: (reviewId: Int?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRoundRect(
                    ZSColor.Neutral50,
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                )
            }
            .padding(horizontal = 22.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "리뷰 ($reviewCount)",
            style = H2,
            color = ZSColor.Neutral900,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = ZSColor.Neutral200,
            onClick = {
                onClickReview(null)
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(text = "$totalRating", color = ZSColor.Neutral900, style = H2)
                Row {
                    repeat(5) {
                        Image(
                            painter = if (it - 1 < totalRating) painterResource(id = R.drawable.ic_star_filled) else painterResource(
                                id = R.drawable.ic_star_gray
                            ), contentDescription = "REVIEW RATING",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(reviewThumbnails) {
                SimpleReviewComponent(
                    rating = it.rating.toInt(),
                    description = it.reviewContents ?: "",
                    writtenAt = it.regDate,
                    onclick = { onClickReview(it.reviewId) }
                )
            }
        }
    }
}

@Composable
fun SimpleReviewComponent(
    rating: Int,
    description: String,
    writtenAt: String,
    onclick: () -> Unit
) {
    Surface(
        color = Color.White,
        border = BorderStroke(1.dp, ZSColor.Neutral100),
        shape = RoundedCornerShape(10.dp),
        onClick = onclick
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .width(300.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Image(
                        painter = if (it - 1 < rating) painterResource(id = R.drawable.ic_star_filled) else painterResource(
                            id = R.drawable.ic_star_gray
                        ), contentDescription = "REVIEW RATING",
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "$rating", style = Body3, color = ZSColor.Neutral900)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = writtenAt, style = Body4, color = ZSColor.Neutral400)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = description,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = Body2,
                color = ZSColor.Neutral700
            )
        }
    }
}

@Composable
private fun SimilarComponent(
    relatedProduct: List<RelatedProduct>,
    productClicked: (productId: Int) -> Unit
) {
    Column(modifier = Modifier
        .drawBehind {
            drawRect(Color.White)
        }
        .padding(vertical = 30.dp)
        .padding(start = 22.dp, end = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "이 상품과 비슷한 상품이에요", style = H1, color = ZSColor.Neutral900)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "더보기", style = Caption, color = ZSColor.Neutral700)
            Image(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "more",
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            contentPadding = PaddingValues(start = 22.dp, end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(relatedProduct) {
                SimpleCardComponent(
                    title = it.productName,
                    image = it.image ?: "",
                    reviewRating = it.rating,
                    reviewCount = it.reviewCount,
                    onClick = { productClicked(it.productId) })
            }
        }
    }
}