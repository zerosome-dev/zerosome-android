package com.zerosome.design.extension

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
fun ChangeSystemColor(
    statusBarColor: Color = Color.White,
    navigationBarColor: Color = Color.Transparent
) {
    val currentContext = LocalContext.current
    (currentContext as ComponentActivity).enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.light(statusBarColor.toArgb(), statusBarColor.toArgb()),
        navigationBarStyle = SystemBarStyle.light(navigationBarColor.toArgb(), navigationBarColor.toArgb())
    )

}