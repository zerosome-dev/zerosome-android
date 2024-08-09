package com.zerosome.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.extension.ChangeSystemColor
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onMoveToLogin: () -> Unit,
    onMoveToMain: () -> Unit
) {
    val effect by viewModel.uiEffect.collectAsState(initial = null)
    LaunchedEffect(key1 = effect) {
        when (effect) {
            is SplashEffect.MoveToLogin -> onMoveToLogin()
            is SplashEffect.MoveToMain -> onMoveToMain()
            else -> {}
        }

    }
    ChangeSystemColor(statusBarColor = ZSColor.Primary)
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            drawRect(color = ZSColor.Primary)
        }) {
        Image(painter = painterResource(id = com.zerosome.design.R.drawable.ic_logo_main), contentDescription = "", modifier = Modifier.align(Alignment.Center))
    }
}