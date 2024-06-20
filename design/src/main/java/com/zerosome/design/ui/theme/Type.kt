@file:Suppress("UNUSED")

package com.zerosome.design.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zerosome.design.R
import com.zerosome.design.extension.toSp

private val PretendardFontFamily = FontFamily(
    listOf(
        Font(R.font.pretendard_black, weight = FontWeight.Black, style = FontStyle.Normal),
        Font(R.font.pretendard_extra_bold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
        Font(R.font.pretendard_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
        Font(R.font.pretendard_semi_bold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
        Font(R.font.pretendard_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
        Font(R.font.pretendard_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(R.font.pretendard_light, weight = FontWeight.Light, style = FontStyle.Normal),
        Font(
            R.font.pretendard_extra_light,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Normal
        ),
        Font(R.font.pretendard_thin, weight = FontWeight.Thin, style = FontStyle.Normal)
    )
)

object ZSTextStyle

val H1
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 20.dp.toSp(),
        fontWeight = FontWeight.Bold,
        lineHeight = (20.dp.times(other = 1.35f).toSp()),
        letterSpacing = (-0.01).sp
    )

val H2
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 18.dp.toSp(),
        fontWeight = FontWeight.Bold,
        lineHeight = (18.dp.times(other = 1.35f).toSp()),
        letterSpacing = (-0.01).sp
    )

val SubTitle1
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.dp.toSp(),
        lineHeight = (15.dp.times(1.4f).toSp()),
    )

val SubTitle2
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.dp.toSp(),
        lineHeight = (14.dp.times(1.4f).toSp()),
    )

val Body1
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 15.dp.toSp(),
        fontWeight = FontWeight.Medium,
        lineHeight = (15.dp.times(1.4f).toSp()),
    )


val Body2
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 14.dp.toSp(),
        fontWeight = FontWeight.Medium,
        lineHeight = (14.dp.times(1.4f).toSp()),
    )

val Body3
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 13.dp.toSp(),
        fontWeight = FontWeight.Medium,
        lineHeight = (14.dp.times(1.4f).toSp()),
    )


val Body4
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 12.dp.toSp(),
        fontWeight = FontWeight.Medium,
    )

val Label1
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 13.dp.toSp(),
        fontWeight = FontWeight.Medium,
        lineHeight = (13.dp.times(1.4f).toSp()),
    )

val Label2
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 11.dp.toSp(),
        fontWeight = FontWeight.Medium,
        lineHeight = (13.dp).times(1.4f).toSp()
    )

val Caption
    @Composable
    get() = TextStyle(
        fontFamily = PretendardFontFamily,
        fontSize = 13.dp.toSp(),
        fontWeight = FontWeight.Medium,
        lineHeight = (13.dp.times(1.4f).toSp()),
    )