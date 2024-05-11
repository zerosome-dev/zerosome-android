package com.zerosome.design.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@Composable
fun Dp.toSp(): TextUnit {
    val density = LocalDensity.current

    return TextUnit(((value * density.density) / density.fontScale), TextUnitType.Sp)
}