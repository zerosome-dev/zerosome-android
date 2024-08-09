package com.zerosome.review

open class ReviewDestination {
    open val route: String = "Review"
}

internal object ReviewListDestination: ReviewDestination() {
    override val route: String = "${super.route}/List"
}

internal object ReviewWriteDestination: ReviewDestination() {
    override val route: String = "${super.route}/Write"
}