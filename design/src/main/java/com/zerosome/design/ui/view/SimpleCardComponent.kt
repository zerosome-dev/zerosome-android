package com.zerosome.design.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zerosome.design.R
import com.zerosome.design.ui.component.ZSImage
import com.zerosome.design.ui.component.ZSVector
import com.zerosome.design.ui.theme.Body3
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.SubTitle2
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun SimpleCardComponent(
    modifier: Modifier = Modifier,
    title: String,
    brandName: String? = null,
    reviewRating: Float = 0f,
    reviewCount: Int = 0,
    image: String,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable(interactionSource = remember {
        MutableInteractionSource()
    }, indication = rememberRipple(), role = Role.Button, onClick = onClick)) {
        ZSImage(
            imageString = image,
            contentDescription = "image",
            scale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .aspectRatio(1f)
                .background(ZSColor.Neutral100, RoundedCornerShape(10.dp))
                .clip(
                    RoundedCornerShape(10.dp)
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        brandName?.let {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = brandName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = SubTitle2,
                    color = ZSColor.Neutral500
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = SubTitle1,
            color = ZSColor.Neutral900,
            modifier = Modifier.width(150.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            ZSVector(
                imageVectorResource = R.drawable.ic_star_filled,
                modifier = Modifier.size(16.dp),
                scale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "$reviewRating", style = Body3, color = ZSColor.Neutral400)
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "($reviewCount)", style = Body3, color = ZSColor.Neutral400)

        }
    }
}