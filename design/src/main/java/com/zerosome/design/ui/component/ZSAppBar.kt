package com.zerosome.design.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun ZSAppBar(
    backNavigationIcon: Painter? = null,
    onBackPressed: () -> Unit = {},
    navTitle: String, // TODO : WILL CHANGE TO ResId,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 18.dp, vertical = 12.dp)) {
        backNavigationIcon?.let {
            Image(painter = it, contentDescription = "Back Pressed", modifier = Modifier
                .size(24.dp)
                .clickable(
                    onClick = onBackPressed,
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(),
                    role = Role.Button
                )
                .align(Alignment.CenterStart))
            
            Text(text = navTitle, style = SubTitle1, color = ZSColor.Neutral900, modifier = Modifier.align(Alignment.Center))
        }
    }
}