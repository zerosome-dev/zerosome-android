package com.zerosome.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.component.PageIndicator
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun ImageHorizontalPager(
    modifier: Modifier = Modifier,
    listItems: List<String>,
) {
    val pagerState = rememberPagerState(0, pageCount = { listItems.size })
    Box(modifier.fillMaxWidth().background(color = ZSColor.Neutral300)) {
        HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) {
            Text(modifier = Modifier.fillMaxWidth().align(Alignment.Center), text = "PAGE $it", style = H1, textAlign = TextAlign.Center)
        }
        PageIndicator(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
            pagerState = pagerState
        )
    }
}
