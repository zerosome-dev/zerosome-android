package com.zerosome.onboarding

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zerosome.design.ui.theme.Label2

@Composable
internal fun TermsScreen(moveToNext: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        Row(modifier = Modifier.fillMaxSize()){
            Button(onClick = {
                moveToNext()
            }) {
                Text("약관 페이지", style = Label2)
            }
        }
    }
}