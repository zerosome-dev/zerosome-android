package com.zerosome.main

import android.util.Log
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
import com.zerosome.main.detail.ProductDetailScreen
import com.zerosome.main.home.HomeScreen
import com.zerosome.main.home.RolloutScreen
import com.zerosome.profile.profileNavigation
import com.zerosome.report.ReportDestination
import com.zerosome.report.reportNavigation
import com.zerosome.review.ReviewDestination
import com.zerosome.review.ReviewWriteScreen
import com.zerosome.review.reviewNavigation

@Composable
fun MainScreen(onMoveToLogin: () -> Unit) {
    val navController = rememberNavController()
    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            startDestination = Home.route
        ) {
            composable(Home.route) {
                HomeScreen(onClickProduct = { productId ->
                    Log.d("CPRI", "PRODUCT ID $productId")
                    navController.navigate("${ProductDetail.route}/$productId")
                }, onClickMore = {
                    navController.navigate(Rollout.route)
                })
            }
            composable(Category.route) {
                CategorySelectionScreen(
                    onCategorySelected = { depth1, depth2 ->
                        Log.d("CPRI", "ROUTE : \"${CategoryDetail.route}/$depth1/$depth2\"")
                        navController.navigate("${CategoryDetail.route}/$depth1/$depth2")
                    }
                )
            }
            profileNavigation(navController, onMoveToLogin = onMoveToLogin)
            composable(Rollout.route) {
                RolloutScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    onClickProduct = {
                        Log.d("CPRI", "PRODUCT $it")
                        navController.navigate("${ProductDetail.route}/$it")
                    }
                )
            }
            composable(
                "${CategoryDetail.route}/{${CategoryDetail.category1}}/{${CategoryDetail.category2}}",
                arguments = CategoryDetail.argument
            ) {
                val firstCategory = it.arguments?.getString(CategoryDetail.category1)
                val secondCategory = it.arguments?.getString(CategoryDetail.category2)
                CategoryDetailScreen(
                    category1Id = requireNotNull(firstCategory) { "Argument Must be passed " },
                    category2Id = secondCategory,
                    onBackPressed = { navController.popBackStack() },
                    onClickProduct = { productId ->
                        navController.navigate("${ProductDetail.route}/$productId")
                    }
                )
            }
            composable(
                "${ProductDetail.route}/{${ProductDetail.argumentTypeArg}}",
                arguments = ProductDetail.argument
            ) {
                val accountTypeArgs = it.arguments?.getInt(ProductDetail.argumentTypeArg)
                ProductDetailScreen(
                    productId = requireNotNull(accountTypeArgs) { "Argument Must be Passed " },
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    onClickReview = { _, _ ->
                        navController.navigate(ReviewDestination().route)
                    }, onClickWriteReview = {
                        navController.navigate(ReviewWrite.route)
                    }, onClickSimilarProduct = { productId ->
                        navController.navigate("${ProductDetail.route}/$productId")
                    })
            }
            composable(ReviewWrite.route) {
                ReviewWriteScreen {
                    navController.popBackStack()
                }
            }
            reviewNavigation(0, navController) {
                navController.navigate(ReportDestination().route)
            }
            reportNavigation(0) {
                navController.popBackStack()
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