package com.zerosome.design.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zerosome.design.R
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.SubTitle2
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun SimpleCardComponent(
    modifier: Modifier = Modifier,
    title: String,
    brandName: String,
    image: String
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "image",
            modifier = modifier
                .background(ZSColor.Neutral100, RoundedCornerShape(10.dp))
                .defaultMinSize(150.dp, 150.dp)
                .aspectRatio(1f)
                .clip(
                    RoundedCornerShape(10.dp)
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = brandName, maxLines = 1, overflow = TextOverflow.Ellipsis, style = SubTitle2, color = ZSColor.Neutral500)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis, style = SubTitle1)
    }
}