package com.zerosome.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.extension.ChangeSystemColor
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.ZSColor

@Composable
internal fun LoginScreen(
    moveToNext: (customAccessToken: String, userType: LoginType) -> Unit,
    moveToMain: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val effect by viewModel.uiEffect.collectAsState(initial = null)
    LaunchedEffect(key1 = effect) {
        when (effect) {
            LoginEffect.OpenKakao -> {
                val accessToken = context.requestKakaoLogin()
                if (accessToken.isNotEmpty()) {
                    viewModel.setAction(LoginAction.CheckKakaoLogin(accessToken))
                }
            }

            LoginEffect.NavigateToMain -> {
                moveToMain()
            }

            is LoginEffect.NavigateToTermsAgree -> {
                moveToNext(
                    (effect as LoginEffect.NavigateToTermsAgree).accessToken,
                    (effect as LoginEffect.NavigateToTermsAgree).userType
                )
            }

            else -> {}
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ZSColor.Primary
    ) {
        ChangeSystemColor(
            statusBarColor = ZSColor.Primary
        )
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(46.dp))
            Image(
                painter = painterResource(id = com.zerosome.design.R.drawable.ic_logo_main),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .clickable {
                        viewModel.setAction(LoginAction.ClickKakaoLogin)
                    },
                painter = painterResource(id = com.zerosome.design.R.drawable.img_kakao_login),
                contentScale = ContentScale.Crop,
                contentDescription = "KAKAO_LOGIN"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = com.zerosome.design.R.string.screen_login_context01),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { viewModel.setAction(LoginAction.ClickSkip) }
                    .drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height
                        drawLine(
                            color = Color.White,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
                textAlign = TextAlign.Center, style = Body2,
            )
            Spacer(modifier = Modifier.weight(1f))

        }
    }
}