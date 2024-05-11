package com.zerosome.design

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@ExperimentalFoundationApi
@Composable
fun ImageHorizontalPager(
    modifier: Modifier = Modifier,
    listItems: List<String>,
    onImageClick: ((rawUrl: String, index: Int) -> Unit)? = null
) {
    val pagerState = rememberPagerState(0, pageCount = { listItems.size })
    Box(modifier) {
        HorizontalPager(state = pagerState) {

        }
    }
}