package com.zerosome.design.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.component.ZSTag
import com.zerosome.design.ui.theme.Label1
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun DetailCardComponent() {
    Surface(shape = RoundedCornerShape(12.dp), modifier = Modifier
        .wrapContentSize(),
        shadowElevation = 5.dp)
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier
                .height(216.dp)
                .aspectRatio(75/56f)
                .background(
                    color = ZSColor.Neutral500,
                ))
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "#생수/음료 #탄산음료", style = Label1, color = ZSColor.Neutral500)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "상품명 상품명", maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(horizontal = 66.dp), style = SubTitle1)
            Spacer(modifier = Modifier.height(15.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
               listOf("쿠팡", "이마트").forEach {
                   ZSTag(title = it)
               }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}