package com.zerosome.review

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.Body3
import com.zerosome.design.ui.theme.Body4
import com.zerosome.design.ui.theme.SubTitle2
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun ReviewListComponent(
    authorName: String,
    dateString: String,
    rating: Float,
    reviewString: String,
    isLast: Boolean = false,
    onClickReportReview: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val isExpandable by remember { derivedStateOf { textLayoutResult?.didOverflowHeight ?: false } }
    val isButtonShown by remember { derivedStateOf { isExpandable || expanded } }
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        ) {
            Text(
                authorName,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = SubTitle2,
                color = ZSColor.Neutral700,
                modifier = Modifier.weight(1f)
            )
            Text(text = dateString, style = Body4, color = ZSColor.Neutral400)
        }
        Spacer(Modifier.height(4.dp))
        ReviewRatingComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            rating = rating
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = reviewString,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .animateContentSize(
                    tween(200)
                ),
            style = Body2,
            color = ZSColor.Neutral700,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult = it }
        )
        Spacer(Modifier.height(if (isButtonShown) 16.dp else 20.dp))
        if (isButtonShown) {
            Text(
                text = if (expanded) "접기" else "더보기",
                style = Body3,
                color = ZSColor.Neutral600,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { expanded = expanded.not() })
            Spacer(Modifier.height(2.dp))
        }
        Text(text = "신고", style = Body3, color = ZSColor.Neutral300, modifier = Modifier
            .padding(start = 22.dp)
            .clickable { onClickReportReview() })
        Spacer(modifier = Modifier.height(30.dp))
        if (isLast.not()) {
            HorizontalDivider(color = ZSColor.Neutral100)
        }
    }
}


@Composable
fun ReviewRatingComponent(modifier: Modifier = Modifier, rating: Float) {
    Row(modifier = modifier) {
        repeat(5) {
            Image(
                painter = if (it < rating) painterResource(com.zerosome.design.R.drawable.ic_star_filled) else painterResource(
                    com.zerosome.design.R.drawable.ic_star_gray
                ),
                contentDescription = "USER RATING",
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text("$rating", color = ZSColor.Neutral700, style = Body4)
    }
}