package com.zerosome.design.ui.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun CommonTitleView(
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int? = null
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
        .padding(horizontal = 22.dp)) {
        Text(text = stringResource(id = titleRes), style = H1, color = Color.Black)
        Spacer(modifier = Modifier.height(6.dp))
        descriptionRes?.let {
            Text(text = stringResource(id = it), style = Body2, color = ZSColor.Neutral500)
        }
    }
}