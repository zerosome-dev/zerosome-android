package com.zerosome.main

open class MainDestination {
    open val route: String = "Main"
}

internal object Home: MainDestination() {
    override val route: String = "${super.route}/Home"
}

internal object Category: MainDestination() {
    override val route: String = "${super.route}/Category"
}

internal object Profile: MainDestination() {
    override val route: String = "${super.route}/MyPage"
}

internal object CategoryDetail: MainDestination() {
    override val route: String
        get() = "${super.route}/CategoryDetail"
}

internal object ProductDetail: MainDestination() {
    override val route: String
        get() = "${super.route}/ProductDetail"
}

internal object ReviewWrite: MainDestination() {
    override val route: String
        get() = "${super.route}/ReviewWrite"
}

sealed class BottomNavItem(
    val title: Int,
    val icon: Int,
    val screenRoute: String
) {
    data object Home: BottomNavItem(title = R.string.nav_title_home, icon = com.zerosome.design.R.drawable.ic_home, screenRoute = com.zerosome.main.Home.route)
    data object Category: BottomNavItem(title = R.string.nav_title_category, icon = com.zerosome.design.R.drawable.ic_search, screenRoute = com.zerosome.main.Category.route)

    data object MyPage: BottomNavItem(title = R.string.nav_title_profile, icon = com.zerosome.design.R.drawable.ic_user, screenRoute = Profile.route)
}