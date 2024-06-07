package com.zerosome.design.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun ZSTag(
    title: String,
    type: ZSTagType = ZSTagType.GRAY
) {
    val backgroundColor by rememberUpdatedState(
        newValue = when (type) {
            ZSTagType.GRAY -> ZSColor.Neutral50
            else -> Color.White
        }
    )
    val textColor by rememberUpdatedState(
        newValue = when (type) {
            ZSTagType.GRAY -> ZSColor.Neutral700
            else -> ZSColor.Neutral600
        }
    )
    val borderStroke by rememberUpdatedState(
        newValue = when (type) {
            ZSTagType.OUTLINE -> BorderStroke(1.dp, color = ZSColor.Neutral100)
            else -> null
        }
    )
    Surface(shape = RoundedCornerShape(4.dp), color = backgroundColor, border = borderStroke) {
        Text(text = title, color = textColor, modifier = Modifier.padding(vertical = 3.dp, horizontal = 6.dp))
    }
}

@Stable
enum class ZSTagType {
    GRAY, OUTLINE
}