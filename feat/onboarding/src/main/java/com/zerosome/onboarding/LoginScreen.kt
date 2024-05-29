package com.zerosome.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zerosome.design.ImageHorizontalPager
import com.zerosome.design.ui.theme.Label2

@Composable
internal fun LoginScreen(
    moveToNext: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding().navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(46.dp))
            ImageHorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp), listItems = listOf(
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                moveToNext()
            }) {
                Text("로그인 페이지", style = Label2)
            }

        }

    }
}