package com.zerosome.design.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ZSButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enable: Boolean = true,
    scope: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        enabled = enable,
        contentPadding = PaddingValues(vertical = 15.dp)
    ) {
        scope()
    }
}