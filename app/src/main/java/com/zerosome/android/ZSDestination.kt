package com.zerosome.android

interface ZSDestination {
    val route: String
}

data object Main : ZSDestination{
    override val route: String
        get() = "Main"
}

data object Onboarding: ZSDestination {
    override val route: String
        get() = "onboarding"

}

data object Splash: ZSDestination {
    override val route: String
        get() = "Splash"

}