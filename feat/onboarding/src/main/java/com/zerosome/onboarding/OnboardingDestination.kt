package com.zerosome.onboarding

open class OnboardingDestination {
    open val route: String
        get() = "onboarding"
}

internal data object Terms: OnboardingDestination() {
    override val route: String
        get() = "${super.route}/terms"
}

internal data object Login: OnboardingDestination() {
    override val route: String = "${super.route}/login"
}

internal data object Nickname: OnboardingDestination() {
    override val route: String = "${super.route}/nickname"
}