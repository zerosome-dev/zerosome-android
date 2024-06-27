package com.zerosome.onboarding

import android.util.Log
import com.zerosome.data.repository.UserRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateNicknameUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(nickname: String): Flow<NetworkResult<Boolean>> = flow {
        Log.d("CPRI", "VALIDATE STARTED")
        emit(NetworkResult.Loading)
        kotlinx.coroutines.delay(1000)
        if (nickname.isNotEmpty()) {
            emit(NetworkResult.Success(true))
        } else {
            emit(NetworkResult.Success(false))
        }
        // repository.validateNickname 설정 필요.
    }
}