package com.zerosome.design.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.ZSColor
import kotlin.math.absoluteValue

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    selectedColor: Color = Color.Black,
    defaultColor: Color = ZSColor.Neutral500,
    space: Dp = 6.dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space),
        ) {
            repeat(pagerState.pageCount) {
                Box(modifier = Modifier
                    .size(6.dp).background(defaultColor, shape = CircleShape))
            }
        }

        Box(
            modifier = Modifier
                .jumpingDotTransition(pagerState, jumpScale = 0.8f)
                .size(6.dp)
                .background(selectedColor, shape = CircleShape)
        )
    }

}

@Composable
private fun Modifier.jumpingDotTransition(pagerState: PagerState, jumpScale: Float) =
    graphicsLayer {
        val pageOffset = pagerState.currentPageOffsetFraction
        val scrollPosition = pagerState.currentPage + pageOffset
        translationX = scrollPosition * (size.width + 6.dp.roundToPx())

        val targetScale = jumpScale - 1f
        val scale = if (pageOffset.absoluteValue < .5) {
            1.0f + (pageOffset.absoluteValue * 2) * targetScale
        } else {
            jumpScale + ((1 - (pageOffset.absoluteValue * 2) * targetScale))
        }

        scaleX = scale
        scaleY = scale
    }
