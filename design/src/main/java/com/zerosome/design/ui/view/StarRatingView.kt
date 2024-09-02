package com.zerosome.design.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.GestureStrategy
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.zerosome.design.R
import com.zerosome.design.ui.component.ZSVector
import com.zerosome.design.ui.theme.ZSColor
import kotlin.math.roundToInt

@Composable
fun StarRatingView(
    rating: Int,
    modifier: Modifier,
    onRatingChanged: (Int) -> Unit,
) {
    Row(
        modifier = modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        for (i in 1..5) {
            val isSelected = i <= rating
            val icon = if (isSelected) ImageVector.vectorResource(id = R.drawable.ic_star_filled) else ImageVector.vectorResource(
                id = R.drawable.ic_star_gray
            )
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i)
                        }
                    )
                    .size(36.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }

}