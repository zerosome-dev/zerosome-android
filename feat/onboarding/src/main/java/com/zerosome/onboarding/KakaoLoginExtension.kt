package com.zerosome.onboarding

import android.content.Context
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun Context.requestKakaoLogin(): String {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
        return suspendCancellableCoroutine<String> {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                error?.let { throwable ->
                    Log.d("CPRI", "ERROR CAUSE $it")
                    it.cancel(throwable)
                } ?: token?.let { safeToken ->
                    Log.d("CPRI", "TOKEN RECEIVED $it")
                    it.resume(safeToken.accessToken)
                } ?: it.cancel()
            }
        }
    } else return ""
}