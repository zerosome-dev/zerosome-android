package com.zerosome.onboarding

import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zerosome.design.ImageHorizontalPager
import com.zerosome.design.extension.ChangeSystemColor
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.theme.Label2
import com.zerosome.design.ui.theme.ZSColor

@Composable
internal fun LoginScreen(
    moveToNext: () -> Unit
) {
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
            ZSButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp), onClick = {
                moveToNext()
            }) {
                Text("로그인 페이지", style = Label2)
            }

        }
    }
}