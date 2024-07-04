package com.zerosome.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.Body1
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.DetailCardComponent
import com.zerosome.design.ui.view.PageIndicator

@Composable
fun ImageHorizontalPager(
    modifier: Modifier = Modifier,
    listItems: List<String>,
) {
    val pagerState = rememberPagerState(0, pageCount = { listItems.size })
    Box(
        modifier
            .fillMaxWidth()
            .background(color = ZSColor.Neutral300)) {
        HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) {
            Text(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center), text = "PAGE $it", style = H1, textAlign = TextAlign.Center)
        }
        PageIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            pagerState = pagerState
        )
    }
}

@Composable
fun CardHorizontalPager(
    modifier: Modifier = Modifier,
    listItems: List<String>,
    onItemClick: () -> Unit,
    isMoreVisible: Boolean = false,
    onMoreClick: () -> Unit = {}
) {
    val pagerState = rememberPagerState(0, pageCount = { if (isMoreVisible) listItems.size + 1 else listItems.size})
    Column(modifier
        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(modifier = Modifier.fillMaxWidth(), state = pagerState, pageSize = PageSize.Fixed(300.dp), contentPadding = PaddingValues(horizontal = 22.dp), pageSpacing = 14.dp ) { page ->
            if (page == pagerState.pageCount - 1 && isMoreVisible) {
                MoreCardComponent(onClick = onMoreClick)
            } else {
                DetailCardComponent(onProductClick = onItemClick)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun MoreCardComponent(
    onClick: () -> Unit,
) {
    Surface(shape = RoundedCornerShape(12.dp), modifier = Modifier
        .height(327.dp)
        .aspectRatio(100 / 109f),
        onClick = onClick
        ,shadowElevation = 5.dp)
    {
        Column(modifier= Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = "MORE")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "더 보기", style = Body1, color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))

        }

    }
}
