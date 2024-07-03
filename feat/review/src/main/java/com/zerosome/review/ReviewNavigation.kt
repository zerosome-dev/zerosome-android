package com.zerosome.review

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.reviewNavigation(
    reviewId: Int,
    navController: NavController,
) {
    navigation(route = ReviewDestination().route, startDestination = ReviewListDestination.route) {
        composable(ReviewWriteDestination.route) {
            ReviewWriteScreen {
                navController.popBackStack()
            }
        }
        composable(ReviewListDestination.route) {
            ReviewScreen(onBackPressed = {
                navController.popBackStack()
            }, onReviewWrite = {
                navController.navigate(ReviewWriteDestination.route)
            })
        }
    }
}