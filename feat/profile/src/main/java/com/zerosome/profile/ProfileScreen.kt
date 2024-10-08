package com.zerosome.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.ui.component.ButtonSize
import com.zerosome.design.ui.component.ButtonType
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.theme.Body3
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.SubTitle2
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.CommonListComponent

@Composable
internal fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    moveToWeb: () -> Unit,
    onClickChangeNickname: () -> Unit,
    onMoveToLogin: () -> Unit,
) {
    val context = LocalContext.current
    val effect by viewModel.uiEffect.collectAsState(initial = null)
    val packageManager = context.packageManager
    LaunchedEffect(key1 = effect) {
        when (effect) {
            is ProfileEffect.MoveToLogin -> onMoveToLogin()
            else -> {}
        }
    }
    ZSScreen(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(), isLoading = viewModel.isLoading
    ) {
        Text(
            modifier = Modifier
                .padding(start = 22.dp)
                .padding(vertical = 10.dp),
            text = "마이 페이지",
            style = H1,
            color = ZSColor.Neutral900
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                viewModel.uiState.profile?.nickname ?: "",
                style = SubTitle1,
                color = ZSColor.Neutral900
            )
            Spacer(modifier = Modifier.weight(1f))
            ZSButton(
                buttonType = ButtonType.SECONDARY,
                onClick = onClickChangeNickname,
                buttonSize = ButtonSize.SMALL
            ) {
                Text("닉네임 변경", style = Body3, color = ZSColor.Neutral600)
            }
        }
        Spacer(Modifier.height(35.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .drawBehind {
                if (viewModel.uiState.profile?.reviewCount?.compareTo(0) == 1) {
                    drawRoundRect(
                        color = ZSColor.Primary,
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )
                } else {
                    drawRoundRect(
                        color = ZSColor.Primary.copy(.1f),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )
                }
            }) {
            Text(
                text = if (viewModel.uiState.profile?.reviewCount?.compareTo(0) == 1) "작성한 리뷰 (${viewModel.uiState.profile?.reviewCount})" else "아직 작성한 리뷰가 없어요",
                style = SubTitle2,
                color = if (viewModel.uiState.profile?.reviewCount?.compareTo(0) == 1) Color.White else ZSColor.Neutral800,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalDivider(thickness = 12.dp, color = ZSColor.Neutral50)
        Spacer(Modifier.height(20.dp))
        Text(
            "고객센터",
            style = Body3,
            color = ZSColor.Neutral400,
            modifier = Modifier.padding(start = 22.dp)
        )
        CommonListComponent(title = "공지사항", modifier = Modifier, onListClick = {})
        CommonListComponent(title = "1:1문의", modifier = Modifier, onListClick = {})
        HorizontalDivider(
            thickness = 1.dp, color = ZSColor.Neutral50, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "서비스 이용",
            style = Body3,
            color = ZSColor.Neutral400,
            modifier = Modifier.padding(start = 22.dp)
        )
        CommonListComponent(title = "서비스 이용약관", modifier = Modifier, onListClick = moveToWeb)
        CommonListComponent(title = "개인정보 처리방침", modifier = Modifier, onListClick = moveToWeb)
        CommonListComponent(
            title = "버전 정보",
            modifier = Modifier,
            suffixText = packageManager.getPackageInfo(context.packageName, 0).versionName
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp), horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            ZSButton(
                onClick = { viewModel.setAction(ProfileAction.ClickLogout) },
                buttonType = ButtonType.OUTLINE,
                buttonSize = ButtonSize.MEDIUM,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "로그아웃", style = Body3, color = ZSColor.Neutral400)
            }
            ZSButton(
                onClick = { },
                buttonType = ButtonType.OUTLINE,
                buttonSize = ButtonSize.MEDIUM,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "회원 탈퇴", style = Body3, color = ZSColor.Neutral400)
            }
        }
    }
}


@Composable
fun ProfileNotLoginScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .drawBehind {
            drawRect(color = Color.White)
        }) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "마이페이지 기능은 준비중입니다.\n다음 업데이트를 기다려주세요!",
            style = H1,
            color = ZSColor.Neutral900,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}