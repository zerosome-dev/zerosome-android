package com.zerosome.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun OnboardingNavScreen(
    onUserLoggedIn: () -> Unit,
) {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Login.route,
        route = OnboardingDestination().route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Login.route) {
            LoginScreen {
                navHostController.navigate(Terms.route)
            }
        }
        composable(route = Terms.route) {
            TermsScreen {
                navHostController.navigate(Nickname.route)
            }
        }
        composable(route = Nickname.route) {
            NicknameScreen { onUserLoggedIn() }
        }
    }

}