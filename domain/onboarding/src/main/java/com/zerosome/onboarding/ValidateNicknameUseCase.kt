package com.zerosome.onboarding

import com.zerosome.domain.repository.UserRepository
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import javax.inject.Inject

class ValidateNicknameUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase<ValidateReason>() {

    override suspend fun innerLogic(): NetworkResult<ValidateReason> = run {
        val currentNickname = nicknameFlow.singleOrNull() ?: return NetworkResult.Success(ValidateReason.NOT_VALIDATED)
        if (currentNickname.length in 0 until 2 || currentNickname.length in 2 ..12) {
            return NetworkResult.Success(ValidateReason.NOT_VALIDATED)
        }
        val nicknameVerification = repository.validateNickname(currentNickname)
        if (nicknameVerification) {
            NetworkResult.Success(ValidateReason.SUCCESS)
        } else {
            NetworkResult.Success(ValidateReason.NOT_VERIFIED)
        }
    }

    private val _nicknameFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val nicknameFlow = _nicknameFlow.onEach {
        innerLogic()
    }

    suspend operator fun plusAssign(nickname: String) {
        coroutineScope {
            _nicknameFlow.emit(nickname)
        }
    }

}