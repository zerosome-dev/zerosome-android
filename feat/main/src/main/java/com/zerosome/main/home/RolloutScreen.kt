package com.zerosome.main.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zerosome.design.R
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.view.CommonTitleView
import com.zerosome.design.ui.view.SimpleCardComponent

@Composable
fun RolloutScreen(
    onBackPressed: () -> Unit,
    onClickProduct: (String) -> Unit, // CLICK PRODUCT,
) {
    BackHandler {
        onBackPressed()
    }
    ZSScreen(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        ZSAppBar(
            navTitle = "",
            onBackPressed = onBackPressed,
            backNavigationIcon = painterResource(id = R.drawable.ic_chevron_left)
        )
        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(10.dp))
            CommonTitleView(
                titleRes = com.zerosome.main.R.string.home_rollout_title,
                descriptionRes = com.zerosome.main.R.string.home_rollout_description
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(start = 22.dp, end = 22.dp, bottom = 50.dp), horizontalArrangement = Arrangement.spacedBy(11.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                items(100) {
                    SimpleCardComponent(title = "브랜드 $it", brandName = "브랜드 $it", image = "") {
                        onClickProduct("$it")
                    }
                }
            }
        }
    }
}