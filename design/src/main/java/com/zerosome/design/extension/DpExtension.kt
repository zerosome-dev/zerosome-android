package com.zerosome.design.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toSp(): TextUnit = with(LocalDensity.current) { this@toSp.toSp() }

