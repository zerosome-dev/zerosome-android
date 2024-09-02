package com.zerosome.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zerosome.webview.WebViewDestination
import com.zerosome.webview.WebViewScreen

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
    onMoveToLogin: () -> Unit,
    onMoveToUrl: (String) -> Unit
) {
    navigation(route = Profile().route, startDestination = Main.route) {
        composable(Main.route) {
            ProfileScreen(
                onClickChangeNickname = {
                    navController.navigate(ChangeNickname.route)
                },
                onMoveToLogin = onMoveToLogin,
                moveToWeb = {
//                    navController.navigate(WebViewDestination.route)
                    onMoveToUrl("https://zerosome.imweb.me/?mode=privacy")
                },
            )
        }
    }
    composable(ChangeNickname.route) {
        ChangeNicknameScreen(onBackPressed = {
            navController.popBackStack()
        })
    }
    composable(WebViewDestination.route) {
        WebViewScreen(url = "")
    }
}
