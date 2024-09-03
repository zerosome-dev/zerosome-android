package com.zerosome.onboarding

import android.content.Context
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun Context.requestKakaoLogin(): String {
    return if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
        suspendCancellableCoroutine {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                error?.let { throwable ->
                    it.cancel(throwable)
                } ?: token?.let { safeToken ->
                    it.resume(safeToken.accessToken)
                } ?: it.cancel()
            }
        }
    } else {
        suspendCancellableCoroutine {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                error?.let { throwable ->
                    it.cancel(throwable)
                } ?: token?.let { safeToken ->
                    it.resume(safeToken.accessToken)
                } ?: it.cancel()
            }
        }
    }
}