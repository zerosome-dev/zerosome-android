package com.zerosome.domain

enum class SortItem(val sortName: String) {
    LATEST("최신 등록 순"),
    RATING_HIGHEST("별점 높은 순"),
    RATING_LOWEST("별점 낮은 순"),
    REVIEW_HIGHEST("리뷰 많은 순"),
    REVIEW_LOWEST("리뷰 적은 순")
}