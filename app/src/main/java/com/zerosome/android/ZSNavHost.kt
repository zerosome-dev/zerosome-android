package com.zerosome.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zerosome.main.MainScreen
import com.zerosome.onboarding.OnboardingNavScreen

@Composable
fun ZSNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Onboarding.route // 차후 DATASTORE의 ACCESSTOKEN REFRESH 여부에 따른 핸들링 처리
    ) {
        composable(Main.route) {
            MainScreen()
        }
        composable(Onboarding.route) {
            OnboardingNavScreen(onUserLoggedIn = {
                navController.navigate(Main.route) {
                    popUpTo(Onboarding.route) {
                        inclusive = true
                    }
                }
            })
        }
    }
}