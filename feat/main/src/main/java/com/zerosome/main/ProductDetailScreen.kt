package com.zerosome.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zerosome.design.R
import com.zerosome.design.ui.view.SimpleCardComponent
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSChip
import com.zerosome.design.ui.component.ZSTag
import com.zerosome.design.ui.component.ZSTagType
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.Caption
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.StarRatingView

@Composable
fun ProductDetailScreen(
    onClickReview: () -> Unit,
    onClickWriteReview: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize().navigationBarsPadding().statusBarsPadding(), contentPadding = PaddingValues(bottom = 20.dp)) {
        item { ItemDetailComponent() }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(ZSColor.Neutral50)
            )
        }
        item { SellerComponent() }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(ZSColor.Neutral50)
            )
        }
        item {
            ReviewComponent(reviewCount = 4, totalRating = 4.3f) {
                onClickReview()
            }
        }
        item {
            SimilarComponent()
        }
        item {
            ZSButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp), onClick = onClickWriteReview
            ) {
                Text(text = "리뷰 작성")
            }
        }
    }
}


@Composable
fun ItemDetailComponent() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(color = ZSColor.Neutral50)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        StarRatingView(modifier = Modifier)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "[브랜드브랜드브랜드]", style = Body2, color = ZSColor.Neutral500)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "브랜드 이름 2", style = SubTitle1)
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SellerComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(text = "오프라인 판매처", style = H1)
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (i in 1..10) {
                ZSTag(title = "판매처 $i", type = ZSTagType.OUTLINE)
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "온라인 판매처", style = H1)
        Spacer(modifier = Modifier.height(16.dp))
        OnlineSellerComponent(sellerName = "네이버 쇼핑")
        Spacer(modifier = Modifier.height(10.dp))
        OnlineSellerComponent(sellerName = "쿠팡")
    }
}

@Composable
private fun OnlineSellerComponent(
    sellerName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ZSColor.Neutral50, shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            text = sellerName, modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 16.dp)
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
    onClickReview: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
                onClickReview()
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

    }
}

@Composable
private fun SimilarComponent() {
    Column(modifier = Modifier.padding(vertical = 30.dp)) {
        Column(modifier = Modifier.padding(start = 22.dp, end = 18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "이 상품과 비슷한 상품이에요", style = H1)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "더보기", style = Caption, color = ZSColor.Neutral700)
                Image(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = "more",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(5) {
                ZSChip(enable = it == 0, chipText = "$it")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            contentPadding = PaddingValues(start = 22.dp, end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(5) {
                SimpleCardComponent(
                    title = "CARD $it",
                    brandName = "BRAND $it",
                    image = "",
                    onClick = {})
            }
        }
    }
}