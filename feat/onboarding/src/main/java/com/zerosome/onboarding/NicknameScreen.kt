package com.zerosome.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.component.ZSTextField
import com.zerosome.design.ui.theme.Label2
import com.zerosome.design.ui.view.CommonTitleView

@Composable
internal fun NicknameScreen(
    onBackPressed: () -> Unit,
    onClickNext: (nickname: String) -> Unit,
    viewModel: NicknameViewModel = hiltViewModel()
) {

    val dialogShown by remember {
        derivedStateOf { viewModel.error.isEmpty().not() }
    }

    ZSScreen(
        modifier = Modifier
            .fillMaxSize(),
        isDialogShown = dialogShown,
        isLoading = viewModel.isLoading,
        onDismiss = { viewModel.clearError() },
        errorMessage = viewModel.error,
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
            text = viewModel.uiState.nickname,
            onTextChanged = { viewModel.setAction(NicknameAction.SetNickname(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            erasable = true,
            placeHolderText = stringResource(
                id = R.string.screen_nickname_textfield_hint
            ),
            positiveText = if (viewModel.uiState.isConfirmed == true) viewModel.uiState.holderTextResId?.let {
                stringResource(
                    id = it
                )
            } else null,
            negativeText = if (viewModel.uiState.isConfirmed == false) viewModel.uiState.holderTextResId?.let {
                stringResource(
                    id = it
                )
            } else null
        )
        Spacer(modifier = Modifier.weight(1f))
        ZSButton(
            onClick = {
                onClickNext(viewModel.uiState.nickname)
            }, enable = viewModel.uiState.canGoNext, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(bottom = 10.dp)
        ) {
            Text(
                "닉네임 설정 화면",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Label2
            )
        }
    }
}