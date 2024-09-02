package com.zerosome.design.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zerosome.design.R
import com.zerosome.design.ui.theme.Body2

@Composable
fun ZSScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isDialogShown: Boolean = false,
    errorMessage: String? = null,
    onDismiss: (() -> Unit)? = null,
    onInputEnabled: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val optionalImePaddingModifier = if (onInputEnabled) Modifier.imePadding() else Modifier
    Box(modifier = modifier.fillMaxSize().then(optionalImePaddingModifier), contentAlignment = Alignment.Center) {
        Column(modifier.fillMaxSize()) {
            content()
        }
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
    if (isDialogShown) {
        Dialog(
            onDismissRequest = { onDismiss?.invoke() },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.error_dialog_description, errorMessage ?: ""),
                        style = Body2,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ZSButton(onClick = { onDismiss?.invoke() }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = stringResource(id = R.string.common_confirm))
                    }
                }
            }

        }
    }

}