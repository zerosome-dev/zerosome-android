package com.zerosome.design.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.zerosome.design.R

@Composable
fun ZSImage(
    modifier: Modifier = Modifier,
    imageString: String,
    contentDescription: String = "",
    scale: ContentScale = ContentScale.None
) {
    AsyncImage(model = imageString, contentDescription = contentDescription, modifier = modifier, contentScale = scale, placeholder = painterResource(
        id = R.drawable.img_product
    ))
}

@Composable
fun ZSImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String = "",
    scale: ContentScale = ContentScale.None
) {
    Image(painter = painter, contentDescription = contentDescription, modifier = modifier, contentScale = scale,)
}