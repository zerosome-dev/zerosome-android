package com.zerosome.domain.model

enum class SortItem(val sortName: String) {
    RECENT("최신 등록 순"),
    REVIEWHIGH("별점 높은 순"),
    REVIEWLOW("별점 낮은 순"),
    REVIEWMANY("리뷰 많은 순"),
    REVIEWFEW("리뷰 적은 순")
    
}