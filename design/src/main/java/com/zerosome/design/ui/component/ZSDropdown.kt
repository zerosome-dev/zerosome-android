package com.zerosome.design.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.zerosome.design.R
import com.zerosome.design.ui.theme.Label1
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun ZSDropdown(
    placeholderText: String = "",
    onItemSelected: () -> Unit,
    selectedString: String = ""
) {
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(),
                role = Role.DropdownList,
                onClick = onItemSelected
            )
            .background(color = ZSColor.Neutral50, shape = RoundedCornerShape(6.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = selectedString.ifEmpty { placeholderText }, style = Label1, modifier = Modifier.padding(vertical = 6.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_chevron_down),
            contentDescription = "SHOW MORE"
        )
        Spacer(modifier = Modifier.width(12.dp))
    }
}