package com.zerosome.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun OnboardingNavScreen(
    onUserLoggedIn: () -> Unit,
) {
    val navHostController = rememberNavController()
    val baseViewModel: OnboardingViewModel = hiltViewModel()
    val effect by baseViewModel.uiEffect.collectAsState(initial = null)
    LaunchedEffect(key1 = effect) {
        when (effect) {
            is OnboardingEffect.NavigateToMain -> {
                onUserLoggedIn()
            }
            else -> {}
        }
    }
    NavHost(
        navController = navHostController,
        startDestination = Login.route,
        route = OnboardingDestination().route,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        composable(route = Login.route) {
            LoginScreen(
                moveToNext = { token, type ->
                    baseViewModel.setAction(
                        OnboardingAction.UserDateReceived(
                            accessToken = token,
                            userType = type
                        )
                    ).also {
                        navHostController.navigate(Terms.route)
                    }
                },
                moveToMain = onUserLoggedIn
            )
        }
        composable(route = Terms.route) {
            TermsScreen(onBackPressed = { navHostController.popBackStack() }, onTermsAgreed = {
                baseViewModel.setAction(OnboardingAction.UserMarketingAgreed(it)).also {
                    navHostController.navigate(Nickname.route)
                }
            })
        }
        composable(route = Nickname.route) {
            NicknameScreen(
                onBackPressed = { navHostController.popBackStack() },
                onClickNext = {
                    baseViewModel.setAction(OnboardingAction.UserNicknameSet(it))
                }
            )
        }
    }

}