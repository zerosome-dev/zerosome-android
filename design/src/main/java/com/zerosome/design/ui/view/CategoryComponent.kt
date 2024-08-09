package com.zerosome.design.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.component.ZSImage
import com.zerosome.design.ui.theme.Body3
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun CategoryComponent(
    isSelected: Boolean,
    cardImage: String,
    cardTitle: String,
    onCategoryClick: (cardTitle: String) -> Unit
) {
    val optionalBorder: Modifier = if (isSelected) Modifier
        .border(1.dp, ZSColor.Primary)
        .clip(
            RoundedCornerShape(8.dp)
        ) else Modifier
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onCategoryClick(cardTitle) }
    ) {
        ZSImage(
            imageString = cardImage, scale = ContentScale.Crop, modifier = Modifier
                .then(optionalBorder)
                .aspectRatio(1f)
        )
        Text(text = cardTitle, style = Body3, color = ZSColor.Neutral900)
    }
}