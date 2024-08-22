package com.zerosome.design.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.GestureStrategy
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.zerosome.design.R
import com.zerosome.design.ui.theme.ZSColor
import kotlin.math.roundToInt

@Composable
fun StarRatingView(
    rating: Int,
    modifier: Modifier,
    onRatingChanged: (Int) -> Unit,
) {
    RatingBar(
        modifier = modifier,
        rating = rating.toFloat(),
        painterEmpty = painterResource(id = R.drawable.ic_star_gray),
        painterFilled = painterResource(id = R.drawable.ic_star_filled),
        tintEmpty = ZSColor.Neutral50,
        tintFilled = ZSColor.Caution,
        gestureStrategy = GestureStrategy.DragAndPress,
        rateChangeStrategy = RateChangeStrategy.InstantChange,
        space = 2.dp
    ) {
        onRatingChanged(it.roundToInt())
    }

}