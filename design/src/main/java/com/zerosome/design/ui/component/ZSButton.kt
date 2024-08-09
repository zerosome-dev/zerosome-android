package com.zerosome.design.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun ZSButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonType: ButtonType = ButtonType.PRIMARY,
    buttonSize: ButtonSize = ButtonSize.LARGE,
    enable: Boolean = true,
    scope: @Composable RowScope.() -> Unit
) {
    if (buttonType == ButtonType.TEXT) {
        TextButton(
            modifier = modifier,
            onClick = onClick,
            enabled = enable,
            interactionSource = remember {
                MutableInteractionSource()
            },

        ) {
            scope()
        }
    } else {
        Button(
            modifier = modifier,
            onClick = onClick,
            shape = RoundedCornerShape(10.dp),
            enabled = enable,
            contentPadding = buttonSize.paddingValues,
            colors = ButtonColors(
                containerColor = buttonType.containerColor,
                contentColor = buttonType.contentColor,
                disabledContentColor = ZSColor.Neutral300,
                disabledContainerColor = ZSColor.Neutral100
            ),
            border = if (buttonType == ButtonType.OUTLINE) BorderStroke(
                1.dp,
                buttonType.contentColor
            ) else null
        ) {
            scope()
        }
    }

}

enum class ButtonType(val containerColor: Color, val contentColor: Color) {
    PRIMARY(ZSColor.Primary, Color.White), SECONDARY(
        ZSColor.Neutral50,
        ZSColor.Neutral600
    ),
    OUTLINE(Color.White, ZSColor.Neutral100), TEXT(Color.White, ZSColor.Neutral900)
}

enum class ButtonSize(val paddingValues: PaddingValues) {
    LARGE(paddingValues = PaddingValues(vertical = 16.dp)), MEDIUM(PaddingValues(vertical = 10.dp, horizontal = 20.dp )), SMALL(PaddingValues(vertical = 6.dp, horizontal = 12.dp)), NONE(
        PaddingValues(0.dp)
    )
}