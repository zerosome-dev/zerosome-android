package com.zerosome.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zerosome.main.MainScreen
import com.zerosome.onboarding.OnboardingNavScreen
import com.zerosome.splash.SplashScreen

@Composable
fun ZSNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Splash.route
    ) {
        composable(Main.route) {
            MainScreen {
                navController.navigate(Splash.route) {
                    popUpTo(Main.route) {
                        inclusive = true
                    }
                }
            }
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
        composable(Splash.route) {
            SplashScreen(onMoveToMain = {
                navController.navigate(Main.route) {
                    popUpTo(Splash.route) {
                        inclusive = true
                    }
                }
            }, onMoveToLogin = {
                navController.navigate(Onboarding.route) {
                    popUpTo(Splash.route) {
                        inclusive = true
                    }
                }
            })
        }
    }
}