package com.zerosome.main.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zerosome.design.ui.component.AppBarItem
import com.zerosome.design.ui.component.SimpleCardComponent
import com.zerosome.design.ui.component.ZSChip
import com.zerosome.design.ui.component.ZSDropdown
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.SubTitle2
import com.zerosome.domain.SortItem

@Composable
fun CategoryDetailScreen(onBackPressed: () -> Unit) {
    var isCategoryDropdownShowing by remember {
        mutableStateOf(false)
    }
    var isBrandDropdownShowing by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        AppBarItem(onBackPressed = onBackPressed, backNavigationIcon = painterResource(id = com.zerosome.design.R.drawable.ic_chevron_left), navTitle = "생수/음료")
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(vertical = 10.dp, horizontal = 22.dp)), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ZSDropdown(onItemSelected = { isCategoryDropdownShowing = true}, selectedString = SortItem.LATEST.sortName)
            ZSDropdown(onItemSelected = { isBrandDropdownShowing = true }, placeholderText = "브랜드")
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(20.dp) ) {
            items(40) {
                SimpleCardComponent(title = "아이템 $it", brandName = "브랜드 $it", image = "", modifier = Modifier.fillMaxWidth())
            }
        }
    }
    if (isCategoryDropdownShowing) {
        CategorySortBottomSheet {
            isCategoryDropdownShowing = false
        }
    }
    if (isBrandDropdownShowing) {
        BrandFilterBottomSheet {
            isBrandDropdownShowing = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySortBottomSheet(onDismissRequest: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        SortItem.entries.forEach { 
            Row(modifier = Modifier.padding(vertical = 14.dp, horizontal = 24.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = it.sortName, style = SubTitle2)
                Image(painter = painterResource(id = com.zerosome.design.R.drawable.icon_check), contentDescription = "CHECK")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BrandFilterBottomSheet(onDismissRequest: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Text(text = "브랜드", style = H2, modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(modifier = Modifier.height(20.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(horizontal = 24.dp)) {
            for (i in 1..32) {
                ZSChip(enable = i % 3 == 0, chipText = "브랜드 $i")
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = onDismissRequest) {
            Text(text = "적용", style = SubTitle1, color = Color.White)
        }
    }
}