package com.zerosome.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zerosome.design.CardHorizontalPager
import com.zerosome.design.ImageHorizontalPager
import com.zerosome.design.ui.component.SimpleCardComponent
import com.zerosome.design.ui.component.ZSChip
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.Caption
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun HomeScreen() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
    ) {
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
        item {
            NewItemComponent()
        }
        item {
            PopularCategoryComponent()
        }
        item {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(color = ZSColor.Neutral50))
            PopularCategoryComponent()
        }
    }
}


@Composable
private fun NewItemComponent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "출시 예정 신상품", style = H2, color = ZSColor.Neutral900, modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 30.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "설명 문구를 입력해주세요", color = ZSColor.Neutral500, style = Body2, modifier = Modifier.padding(start = 20.dp))
        Spacer(modifier = Modifier.height(16.dp))
        CardHorizontalPager(listItems = listOf(
            "1",
            "1",
            "1",
            "1",
            "1",
            "1",
        ))
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun PopularCategoryComponent() {
    Column(modifier = Modifier.padding(vertical = 30.dp)) {
        Column(modifier = Modifier.padding(start = 22.dp, end = 18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "[카페음료] 인기 음료 순위", style = H1)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "더보기", style = Caption, color = ZSColor.Neutral700)
                Image(painter = painterResource(id = com.zerosome.design.R.drawable.ic_chevron_right), contentDescription = "more", modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "설명 문구를 입력해주세요")
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(5) {
                ZSChip(enable = it == 0, chipText = "$it")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(contentPadding = PaddingValues(start = 22.dp, end = 10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(5) {
                SimpleCardComponent(title = "CARD $it", brandName = "BRAND $it", image = "")
            }
        }
    }
}