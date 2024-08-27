package com.zerosome.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

open class Profile {
    open val route: String = "Profile"

    val startDestination: String
        get() = Main.route
}

data object Main : Profile() {
    override val route: String
        get() = "${super.route}/Main"
}

data object ChangeNickname : Profile() {
    override val route: String
        get() = "${super.route}/ChangeNickname"
}

fun NavGraphBuilder.profileNavigation(
    navController: NavController,
    onMoveToLogin: () -> Unit
) {
    navigation(route = Profile().route, startDestination = Main.route) {
        composable(Main.route) {
            ProfileScreen(
                onClickChangeNickname = {
                    navController.navigate(ChangeNickname.route)
                },
                onMoveToLogin = onMoveToLogin
            )
        }
    }
    composable(ChangeNickname.route) {
        ChangeNicknameScreen(onBackPressed = {
            navController.popBackStack()
        })
    }
}
