package com.zerosome.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.Body1
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.Label2
import com.zerosome.design.ui.theme.ZSColor

@Composable
internal fun NicknameScreen(onClickNext: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "닉네임을 설정해주세요", style = H2)
        Text(text = "최소 2자 ~ 12자 이내의 닉네임을 입력해주세요", style = Body2, color = ZSColor.Neutral400)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onClickNext) {
            Text(
                "닉네임 설정 화면",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Label2
            )
        }
    }

}