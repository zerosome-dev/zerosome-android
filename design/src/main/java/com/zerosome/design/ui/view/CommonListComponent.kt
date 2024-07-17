package com.zerosome.design.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zerosome.design.R
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun CommonListComponent(
    modifier: Modifier = Modifier,
    title: String,
    suffixText: String? = null,
    onListClick: (() -> Unit)? = null,
) {
    val optionalClickable: Modifier = onListClick?.let { Modifier.clickable { it.invoke() } } ?: Modifier
    Row(modifier = modifier
        .fillMaxWidth()
        .then(optionalClickable)
        .padding(horizontal = 22.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, style = Body2, color = ZSColor.Neutral900, modifier = Modifier.weight(1f).padding(vertical = 8.dp))
        suffixText?.let {
            Text(text = it, style = Body2, color = ZSColor.Neutral500)
        }
        onListClick?.let {
            Image(painter = painterResource(id = R.drawable.ic_chevron_right), modifier = Modifier.size(24.dp), contentDescription = "LIST_CLICk")
        }
    }
}