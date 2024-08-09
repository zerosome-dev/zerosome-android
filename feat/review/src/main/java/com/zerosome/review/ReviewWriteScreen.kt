package com.zerosome.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.R
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSTextField
import com.zerosome.design.ui.theme.Body4
import com.zerosome.design.ui.theme.Label1
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.theme.ZSTextStyle
import com.zerosome.design.ui.view.StarRatingView

@Composable
fun ReviewWriteScreen(onBackPressed: () -> Unit) {
    var text by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZSAppBar(
                navTitle = "리뷰 작성",
                backNavigationIcon = painterResource(id = R.drawable.ic_chevron_left),
                onBackPressed = onBackPressed
            )
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(25 / 16f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "[브랜드 브랜드 브랜드]", style = Label1, color = ZSColor.Neutral500)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "상품명상품명상품명상품명상품명상품명상품명상품명상품명",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = SubTitle1,
                color = ZSColor.Neutral900
            )
            Spacer(modifier = Modifier.height(30.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "상품은 어떠셨나요?", style = SubTitle1, color = ZSColor.Neutral700)
            Spacer(modifier = Modifier.height(20.dp))
            StarRatingView(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(30.dp))
            ZSTextField(
                text = text, onTextChanged = { text = it }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp), singleLine = false, minLines = 3
            )
            Text(
                text = "${text.length}/1000",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 22.dp),
                style = Body4,
                color = ZSColor.Neutral400
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
        ZSButton(
            onClick = onBackPressed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "작성 완료")
        }
    }
}