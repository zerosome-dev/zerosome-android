package com.zerosome.design.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ZSImage(
    modifier: Modifier = Modifier,
    imageString: String,
    contentDescription: String = "",
    scale: ContentScale = ContentScale.None
) {
    AsyncImage(model = imageString, contentDescription = contentDescription, modifier = modifier, contentScale = scale)

}

@Composable
fun ZSImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String = "",
    scale: ContentScale = ContentScale.None
) {
    AsyncImage(model = painter, contentDescription = contentDescription, modifier = modifier, contentScale = scale)
}