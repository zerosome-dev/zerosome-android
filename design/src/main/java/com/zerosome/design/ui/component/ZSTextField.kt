package com.zerosome.design.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.zerosome.design.R
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.Body4
import com.zerosome.design.ui.theme.ZSColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZSTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    enable: Boolean = true,
    erasable: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeHolderText: String = "",
    singleLine: Boolean = true,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    positiveText: String? = null,
    negativeText: String? = null,
) {
    var isCurrentTextFocused by remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isCurrentTextFocused = it.isFocused
                },
            value = text,
            onValueChange = onTextChanged,
            textStyle = Body2,
            minLines = minLines,
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = text,
                    placeholder = {
                        Text(text = placeHolderText, style = Body2, color = ZSColor.Neutral300)
                    },
                    innerTextField = { innerTextField() },
                    enabled = enable,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    trailingIcon = {
                        if (erasable && text.isNotEmpty()) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "CLEAR",
                                modifier = Modifier
                                    .background(color = ZSColor.Neutral400, CircleShape)
                                    .clickable(role = Role.Button, onClick = { onTextChanged("") }),
                                tint = Color.White
                            )
                        }
                    },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 13.dp),
                    container = {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White, RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    if (isCurrentTextFocused) ZSColor.Neutral300 else ZSColor.Neutral100,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        )
                    }
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
        )
        positiveText?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = it, color = ZSColor.Positive, style = Body4)
        } ?: negativeText?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = it, color = ZSColor.Negative, style = Body4)
        }

    }
}
