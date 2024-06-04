package com.zerosome.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.component.NonLazyVerticalGrid
import com.zerosome.design.ui.theme.Body3
import com.zerosome.design.ui.theme.H1
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.ZSColor

@Composable
fun CategorySelectionScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        Column {
            Row(modifier = Modifier.padding(horizontal = 22.dp, vertical = 10.dp)) {
                Text(text = "카테고리", style = H1)
            }
            LazyColumn(modifier = Modifier.weight(1f)) {
                GridItemSpan(
                    categoryName = "카페 음료",
                    categoryItems = listOf("스타벅스", "메가커피", "빽다방", "투썸플레이스")
                )
                GridItemSpan(
                    categoryName = "생수/음료",
                    categoryItems = listOf(
                        "탄산수",
                        "탄산음료",
                        "커피음료",
                        "차음료",
                        "어린이음료",
                        "무알콜음료",
                        "스포츠음료",
                        "숙취/건강음료"
                    )
                )
                GridItemSpan(categoryName = "과자/아이스크림", categoryItems = listOf("과자", "아이스크림"))
                GridItemSpan(categoryName = "양념/소스", categoryItems = listOf("설탕/향신료", "소스/드레싱"))
            }
        }

    }
}

fun LazyListScope.GridItemSpan(
    categoryName: String,
    categoryItems: List<String>
) {
    item {
        Text(
            text = categoryName, style = H2, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 12.dp).padding(horizontal = 22.dp)
        )
    }
    item {
        NonLazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 22.dp),
            columns = 4,
            itemCount = categoryItems.size,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(color = ZSColor.Neutral50, shape = RoundedCornerShape(8))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = categoryItems[it], style = Body3, textAlign = TextAlign.Center)
            }
        }
    }
    item {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(ZSColor.Neutral50)
            )
        }
    }
}
