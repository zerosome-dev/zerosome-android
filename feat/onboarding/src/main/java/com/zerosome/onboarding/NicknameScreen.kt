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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSTextField
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.Label2
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.CommonTitleView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NicknameScreen(
    onBackPressed: () -> Unit,
    onClickNext: () -> Unit
) {
    var nickNameField by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ZSAppBar(
            navTitle = "",
            backNavigationIcon = painterResource(id = com.zerosome.design.R.drawable.ic_chevron_left),
            onBackPressed = onBackPressed
        )
        CommonTitleView(
            titleRes = R.string.screen_nickname_title,
            descriptionRes = R.string.screen_nickname_description
        )
        Spacer(modifier = Modifier.height(30.dp))
        ZSTextField(
            text = nickNameField,
            onTextChanged = { nickNameField = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            erasable = true,
            placeHolderText = stringResource(
                id = R.string.screen_nickname_textfield_hint
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        ZSButton(onClick = onClickNext, modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(bottom = 10.dp)) {
            Text(
                "닉네임 설정 화면",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Label2
            )
        }
    }
}