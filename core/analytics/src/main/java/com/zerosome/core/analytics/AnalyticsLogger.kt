package com.zerosome.core.analytics

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

public enum class LogName {
    OPEN_APP,
    VIEW_LOGIN,
    CLICK_LOGIN_KAKAO,
    CLICK_LOGIN_APPLE,
    CLICK_LOGIN_SKIP,
    VIEW_SIGNUP_TERMS,
    CLICK_TERMS_ALL,
    CLICK_TERMS_WEB,
    CLICK_TERMS_NEXT,
    VIEW_SIGNUP_NICKNAME,
    VIEW_HOME,
    CLICK_NEW_PRODUCT,
    CLICK_NEW_PRODUCT_MORE,
    CLICK_CAFE_PRODUCT,
    CLICK_CAFE_PRODUCT_MORE,
    VIEW_PRODUCT_DETAIL,
    CLICK_PRODUCT_DETAIL_SHOW_NUTRIENT,
    CLICK_PRODUCT_DETAIL_STORE,
    CLICK_PRODUCT_DETAIL_RELATED_PRODUCT,
    CLICK_PRODUCT_DETAIL_REVIEW,
    CLICK_PRODUCT_DETAIL_REVIEW_WRITE,
    VIEW_REVIEW,
    CLICK_REVIEW_WRITE,
    VIEW_REVIEW_WRITE,
    CLICK_REVIEW_WRITE_CONFIRM
}
enum class LogProperty {
    AUTH_TYPE,
    WEB_URL,
    IS_ALL_AGREED,
    PRODUCT_ID,
    PRODUCT_NAME,
    RELATED_PRODUCT_ID,
    RELATED_PRODUCT_NAME,
    PRODUCT_RATING,
}

class AnalyticsLogger {
    fun logEvent(logName: LogName, propertiesMap: Map<LogProperty, Any?>? = null) {
        Firebase.analytics.logEvent(logName.name, Bundle().apply {
            propertiesMap?.let {
                for ((k, v) in it) {
                    putString(k.name, v.toString())
                }
            }
        })
    }
}