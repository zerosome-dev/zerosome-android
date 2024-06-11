package com.zerosome.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.Label2
import com.zerosome.design.ui.theme.ZSColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NicknameScreen(onClickNext: () -> Unit) {
    var clickNextButton by remember {
        mutableStateOf(false)
    }
    var nickNameField by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 22.dp)) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "닉네임을 설정해주세요", style = H2)
        Text(text = "최소 2자 ~ 12자 이내의 닉네임을 입력해주세요", style = Body2, color = ZSColor.Neutral400)
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(value = nickNameField, onValueChange = { nickNameField = it}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.weight(1f))
        ZSButton(onClick ={ clickNextButton = true }) {
            Text(
                "닉네임 설정 화면",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Label2
            )
        }
    }
    if (clickNextButton) {
        ModalBottomSheet(onDismissRequest = onClickNext) {
            Text(text = "약관에 동의해주세요", modifier = Modifier.fillMaxWidth().padding(20.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                Text(text = "모두 동의", style = H2)
            }
            ZSButton(onClick = onClickNext, modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Text(text = "다음")
            }
        }
    }
}