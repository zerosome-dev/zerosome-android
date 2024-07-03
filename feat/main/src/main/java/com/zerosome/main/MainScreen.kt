package com.zerosome.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zerosome.design.ui.theme.Label2
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.main.category.CategoryDetailScreen
import com.zerosome.main.category.CategorySelectionScreen
import com.zerosome.main.home.HomeScreen
import com.zerosome.main.review.ReviewWriteScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val entry = navController.currentBackStackEntryAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            startDestination = Home.route
        ) {
            composable(Home.route) {
                HomeScreen {
                    navController.navigate(ProductDetail.route)
                }
            }
            composable(Category.route) {
                CategorySelectionScreen {
                    navController.navigate(CategoryDetail.route)
                }
            }
            composable(Profile.route) {
                ProfileScreen()
            }
            composable(CategoryDetail.route) {
                CategoryDetailScreen {
                    navController.popBackStack()
                }
            }
            composable(ProductDetail.route) {
                ProductDetailScreen {
                    navController.navigate(ReviewWrite.route)
                }
            }
            composable(ReviewWrite.route) {
                ReviewWriteScreen {
                    navController.popBackStack()
                }
            }
        }
        BottomNavigationView(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            contentColor = ZSColor.Neutral600,
            navHostController = navController
        )
    }
}

@Composable
fun BottomNavigationView(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    navHostController: NavHostController
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.MyPage
    )

    AnimatedVisibility(
        visible = items.map { it.screenRoute }.contains(currentRoute)
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = 0.dp
        ) {
            items.forEach { item ->
                NavigationBarItem(selected = currentRoute == item.screenRoute, label = {
                    Text(
                        stringResource(item.title),
                        style = Label2,
                        color = if (currentRoute == item.screenRoute) ZSColor.Primary else ZSColor.Neutral300
                    )
                }, icon = {
                    Image(
                        painter = painterResource(if (currentRoute == item.screenRoute) item.selectedIcon else item.unselectedIcon),
                        contentDescription = stringResource(item.title),
                    )
                }, onClick = {
                    navHostController.navigate(item.screenRoute) {
                        navHostController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, alwaysShowLabel = true, colors = NavigationBarItemColors(
                    selectedIconColor = ZSColor.Primary,
                    selectedTextColor = ZSColor.Primary,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = ZSColor.Neutral300,
                    unselectedTextColor = ZSColor.Neutral300,
                    disabledIconColor = Color.Transparent,
                    disabledTextColor = Color.Transparent
                )
                )
            }
        }
    }

}