package com.zerosome.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.CardHorizontalPager
import com.zerosome.design.ImageHorizontalPager
import com.zerosome.design.R
import com.zerosome.design.extension.ChangeSystemColor
import com.zerosome.design.ui.component.ZSImage
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.component.ZSTag
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.Caption
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.Label1
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.SimpleCardComponent
import com.zerosome.domain.model.Cafe

@Composable
internal fun HomeScreen(
    onClickProduct: (productId: Int) -> Unit,
    onClickMore: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    ChangeSystemColor(
        statusBarColor = Color.Transparent
    )
    ZSScreen(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        isLoading = viewModel.isLoading
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_app_logo),
                contentDescription = "",
                modifier = Modifier.padding(start = 22.dp, top = 10.dp, bottom = 9.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(viewModel.uiState.uiModels, key = { it.keyId }) {
                when (it) {
                    is HomeUiModel.Banners -> {
                        ImageHorizontalPager(
                            listItems = it.bannerList.map { it.url }, modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                        )
                    }

                    is HomeUiModel.Rollouts -> NewItemComponent(
                        products = it.rollout,
                        onClickProduct = onClickProduct,
                        onClickMore = onClickMore
                    )

                    is HomeUiModel.Cafes -> CafeCategoryComponent(
                        cafeList = it.cafe,
                        onClickCafe = onClickMore,
                        onClickMore = onClickMore
                    )
                }
            }
        }
    }

}


@Composable
private fun NewItemComponent(
    products: List<com.zerosome.domain.model.Rollout>,
    onClickProduct: (productId: Int) -> Unit, onClickMore: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "출시 예정 신상품", style = H2, color = ZSColor.Neutral900, modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 30.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "설명 문구를 입력해주세요",
            color = ZSColor.Neutral500,
            style = Body2,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CardHorizontalPager(
            itemList = products.map { rollout ->
                {
                    RolloutCardComponent(
                        rollOut = rollout,
                        onClick = { onClickProduct(rollout.id) }
                    )
                }
            },
            isMoreVisible = true,
            moreEnableItem = {
                Image(
                    painter = painterResource(id = R.drawable.card_launch_more),
                    contentDescription = "LAUNCH_MORE",
                    modifier = Modifier
                        .size(width = 300.dp, height = 327.dp)
                        .clickable(interactionSource = remember {
                            MutableInteractionSource()
                        }, indication = rememberRipple(), role = Role.Button, onClick = {
                            onClickMore()
                        }),
                    contentScale = ContentScale.Crop
                )
            },
            indicatorVisible = false
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun RolloutCardComponent(rollOut: com.zerosome.domain.model.Rollout, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .shadow(5.dp, shape = RoundedCornerShape(12.dp))
            .drawBehind {
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                )
            }
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple(), onClick = onClick)
    ) {
        ZSImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            imageString = rollOut.image,
            scale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(text = rollOut.categoryD1, style = Label1, color = ZSColor.Neutral500)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = rollOut.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 66.dp),
            style = SubTitle1,
            color = ZSColor.Neutral900
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            rollOut.salesStore?.forEach {
                if (it.isNotEmpty()) {
                    ZSTag(title = it)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CafeCategoryComponent(
    cafeList: List<Cafe>,
    onClickCafe: () -> Unit,
    onClickMore: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 30.dp)) {
        Column(modifier = Modifier.padding(start = 22.dp, end = 18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "[카페음료] 인기 음료 순위", style = H1, color = Color.Black)
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.clickable(onClick = onClickMore), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "더보기", style = Caption, color = ZSColor.Neutral700)
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        contentDescription = "more",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "트렌디한 카페 음료를 지금 바로 확인해보세요", style = Body2, color = ZSColor.Neutral500)
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            contentPadding = PaddingValues(start = 22.dp, end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(cafeList) { cafe ->
                SimpleCardComponent(
                    modifier = Modifier.size(width = 150.dp, height = 242.dp),
                    title = cafe.name,
                    brandName = "[${cafe.brand}]",
                    image = cafe.image,
                    onClick = onClickCafe,
                    reviewRating = cafe.rating ?: 0f,
                    reviewCount = cafe.reviewCount
                )
            }
        }
    }
}
