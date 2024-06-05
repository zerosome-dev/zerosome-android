package com.zerosome.design.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun ZSTag(
    title: String
) {
    Surface(shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(vertical = 3.dp, horizontal = 6.dp), color = ZSColor.Neutral50) {
        Text(text = title, color = ZSColor.Neutral700)
    }
}