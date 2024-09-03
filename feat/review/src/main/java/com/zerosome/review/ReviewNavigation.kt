package com.zerosome.review

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.reviewNavigation(
    navController: NavController,
    onClickReport: () -> Unit,
) {
    navigation(route = ReviewDestination().route, startDestination = ReviewListDestination.route) {
        composable(ReviewWriteDestination.route) {
            ReviewWriteScreen {
                navController.popBackStack()
            }
        }
        composable(ReviewListDestination.route) {
            ReviewScreen(
                onBackPressed = {
                    navController.popBackStack()
                }, onReviewWrite = {
                    navController.navigate(ReviewWriteDestination.route)
                }, onReviewReport = onClickReport
            )
        }
    }
}