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

    operator fun invoke(nickname: String): Flow<NetworkResult<ValidateReason>> = flow {
        Log.d("CPRI", "VALIDATE STARTED")
        emit(NetworkResult.Loading)

        kotlinx.coroutines.delay(300)
        if (nickname.length in 2..12) {
            emit(NetworkResult.Success(ValidateReason.SUCCESS))
        } else {
            emit(NetworkResult.Success(ValidateReason.NOT_VALIDATED))
        }
        // repository.validateNickname 설정 필요.
    }

}
enum class ValidateReason {
    NOT_VALIDATED, SUCCESS, NOT_VERIFIED,
}