package com.zerosome.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZerosomeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.KAKAO_APP_KEY))
    }
}