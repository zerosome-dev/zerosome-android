package com.zerosome.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        Text("ProfileScreen")
    }
}