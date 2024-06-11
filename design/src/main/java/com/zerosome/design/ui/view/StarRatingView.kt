package com.zerosome.design.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.GestureStrategy
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.zerosome.design.R
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun StarRatingView(
    modifier: Modifier
) {
    var dragY by remember {
        mutableFloatStateOf(0f)
    }
    RatingBar(
        modifier = modifier,
        rating = dragY,
        painterEmpty = painterResource(id = R.drawable.ic_star_gray),
        painterFilled = painterResource(id = R.drawable.ic_star_filled),
        tintEmpty = ZSColor.Neutral50,
        tintFilled = ZSColor.Caution,
        gestureStrategy = GestureStrategy.DragAndPress,
        rateChangeStrategy = RateChangeStrategy.InstantChange,
        space = 2.dp
    ) {
        dragY = it
    }

}