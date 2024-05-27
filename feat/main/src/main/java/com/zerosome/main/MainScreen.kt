package com.zerosome.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zerosome.design.ImageHorizontalPager

@Composable
fun MainScreen() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()) {
        item {
            Spacer(modifier = Modifier.height(46.dp))
            ImageHorizontalPager(modifier = Modifier
                .fillMaxWidth()
                .height(240.dp), listItems = listOf(
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
            ))
        }
    }
}