package com.zerosome.report

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.reportNavigation(
    reviewId: Int,
    onReportSuccess: () -> Unit,
) {
    navigation(route = ReportDestination().route, startDestination = ReportReasonDestination.route) {
        composable(ReportReasonDestination.route) {
            ReportReasonScreen(onBackPressed = onReportSuccess)
        }
    }
}

open class ReportDestination {
    open val route: String
        get() = "Report"

}

internal object ReportReasonDestination: ReportDestination() {
    override val route: String
        get() = "${super.route}/Reason"
}