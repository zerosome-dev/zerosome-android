package com.zerosome.design.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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

@Composable
fun ZSVector(
    imageVectorResource: Int,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    scale: ContentScale = ContentScale.None
) {
    Image(imageVector = ImageVector.vectorResource(id = imageVectorResource), contentDescription = contentDescription, contentScale = scale, modifier = modifier)
}