package com.zerosome.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.ui.component.ButtonSize
import com.zerosome.design.ui.component.ButtonType
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.component.ZSTextField
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor

@Composable
internal fun ChangeNicknameScreen(
    onBackPressed: () -> Unit,
    viewModel: ChangeNicknameViewModel = hiltViewModel()
) {
    BackHandler {
        onBackPressed()
    }
    ZSScreen(
        isLoading = viewModel.isLoading, modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        ZSAppBar(
            navTitle = "닉네임 변경",
            onBackPressed = onBackPressed,
            backNavigationIcon = painterResource(
                id = com.zerosome.design.R.drawable.ic_chevron_left
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = com.zerosome.design.R.string.screen_change_nickname_description),
            style = Body2,
            color = ZSColor.Neutral500,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        )
        Spacer(modifier = Modifier.height(43.dp))
        ZSTextField(
            text = viewModel.uiState.selectedNickname,
            onTextChanged = { viewModel.setAction(ChangeNicknameAction.WriteNickname(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            erasable = true,
            placeHolderText = stringResource(
                id = com.zerosome.design.R.string.screen_change_nickname_textfield_hint
            ),
            positiveText = if (viewModel.uiState.isVerified == true) viewModel.uiState.holderTextResId?.let {
                stringResource(
                    id = it
                )
            } else null,
            negativeText = if (viewModel.uiState.isVerified == false) viewModel.uiState.holderTextResId?.let {
                stringResource(
                    id = it
                )
            } else null
        )
        Spacer(modifier = Modifier.weight(1f))
        ZSButton(
            onClick = { },
            enable = viewModel.uiState.isVerified == true,
            buttonSize = ButtonSize.LARGE,
            buttonType = ButtonType.PRIMARY,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 10.dp)
        ) {
            Text(text = "닉네임 변경 완료", style = SubTitle1, color = Color.White)
        }

    }
}