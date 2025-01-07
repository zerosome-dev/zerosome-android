package com.zerosome.onboarding

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<Unit>() {

    private data class TokenCache(
        val token: String,
        val type: LoginType
    )
    private val _cacheFlow = MutableStateFlow<TokenCache?>(null)
    private val cacheFlow = _cacheFlow.filterNotNull()
        .onEach { +this }
        .launchIn(coroutineScope)

    fun plusAssign(token: String, type: LoginType) {
        coroutineScope.launch {
            _cacheFlow.emit(TokenCache(token, type))
        }
    }
    override suspend fun innerLogic(): NetworkResult<Unit> = run {
        _cacheFlow.value?.let {
            if (userRepository.login(it.token, it.type.name)) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(ClientExceptions(ClientError.LOGIN_NOT_VALIDATED_EXCEPTION))
            }
        } ?: NetworkResult.Error(ClientExceptions(ClientError.TOKEN_NOT_GRANTED))
    }
}