package com.zerosome.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zerosome.design.extension.ChangeSystemColor
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.theme.SubTitle1

@Composable
fun ReviewScreen(onBackPressed: () -> Unit, onReviewWrite: () -> Unit, onReviewReport: () -> Unit) {
    ChangeSystemColor(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent
    )
    ZSScreen(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()) {
        ZSAppBar(painterResource(com.zerosome.design.R.drawable.ic_chevron_left), onBackPressed = {
            onBackPressed()
        }, navTitle = "상품 리뷰")
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 80.dp)) {
                items(10) {
                    ReviewListComponent(
                        "AUTHOR $it",
                        dateString = "2024.02.02",
                        rating = (it % 5),
                        reviewString = "리뷰텍스트 $it",
                        it == 9,
                        onReviewReport
                    )
                }
            }

            ZSButton(modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
                .align(Alignment.BottomCenter), onClick = {
                    onReviewWrite()
            }) {
                Text("리뷰 작성", style = SubTitle1, color = Color.White)
            }
        }
    }

}