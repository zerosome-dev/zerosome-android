package com.zerosome.design.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun ZSChip(
    enable: Boolean,
    chipText: String,
) {

    val backgroundColor: Color by rememberUpdatedState(newValue = if (enable) ZSColor.Primary else ZSColor.Neutral50)
    val borderEnable: BorderStroke? by rememberUpdatedState(newValue = if (enable) BorderStroke(1.dp, color = ZSColor.Neutral200) else null)
    val textColor: Color by rememberUpdatedState(newValue = if (enable) Color.White else ZSColor.Neutral400)
    Surface(
        color = backgroundColor,
        border = borderEnable,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(text = chipText, style = Body2, color = textColor, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
    }
}
