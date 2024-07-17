package com.zerosome.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.zerosome.profile.Profile

open class MainDestination {
    open val route: String = "Main"
}

internal object Home : MainDestination() {
    override val route: String = "${super.route}/Home"
}

internal object Category : MainDestination() {
    override val route: String = "${super.route}/Category"
}

internal object Rollout : MainDestination() {
    override val route: String
        get() = "${super.route}/Rollout"
}

internal object CategoryDetail : MainDestination() {
    override val route: String
        get() = "${super.route}/CategoryDetail"
}

internal object ProductDetail : MainDestination() {
    override val route: String
        get() = "${super.route}/ProductDetail"
}

internal object ReviewWrite : MainDestination() {
    override val route: String
        get() = "${super.route}/ReviewWrite"
}

sealed class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val screenRoute: String
) {
    data object Home : BottomNavItem(
        title = R.string.nav_title_home,
        unselectedIcon = com.zerosome.design.R.drawable.ic_nav_home,
        selectedIcon = com.zerosome.design.R.drawable.ic_nav_home_selected,
        screenRoute = com.zerosome.main.Home.route
    )

    data object Category : BottomNavItem(
        title = R.string.nav_title_category,
        unselectedIcon = com.zerosome.design.R.drawable.ic_nav_category,
        selectedIcon = com.zerosome.design.R.drawable.ic_nav_category_selected,
        screenRoute = com.zerosome.main.Category.route
    )

    data object MyPage : BottomNavItem(
        title = R.string.nav_title_profile,
        unselectedIcon = com.zerosome.design.R.drawable.ic_nav_mypage,
        selectedIcon = com.zerosome.design.R.drawable.ic_nav_mypage_selected,
        screenRoute = Profile().startDestination
    )
}