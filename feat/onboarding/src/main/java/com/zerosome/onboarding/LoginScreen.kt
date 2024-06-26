package com.zerosome.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.ImageHorizontalPager
import com.zerosome.design.extension.ChangeSystemColor
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.Label2
import com.zerosome.design.ui.theme.ZSColor

@Composable
internal fun LoginScreen(
    moveToNext: () -> Unit,
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
                    moveToNext()
                }
            }

            LoginEffect.NavigateToMain -> {
                moveToMain()
            }
            is LoginEffect.NavigateToTermsAgree -> {
               moveToNext()
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
            Spacer(
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(1.dp, color = Color.White, shape = RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .clickable {
                        viewModel.setAction(LoginAction.ClickKakaoLogin)
                    },
                painter = painterResource(id = com.zerosome.onboarding.R.drawable.img_kakao_login),
                contentScale = ContentScale.Crop,
                contentDescription = "KAKAO_LOGIN"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = com.zerosome.onboarding.R.string.screen_login_context01),
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