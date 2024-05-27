package com.zerosome.onboarding

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun NicknameScreen(onClickNext: () -> Unit) {
    Button(onClick = onClickNext) {
        Text("닉네임 설정 화면", modifier = Modifier.fillMaxWidth())
    }
}