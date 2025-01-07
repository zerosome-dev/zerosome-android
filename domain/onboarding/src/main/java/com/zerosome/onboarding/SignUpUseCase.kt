package com.zerosome.onboarding

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<Boolean>() {

    private data class ItemCache(
        val socialType: String,
        val socialToken: String,
        val nickname: String,
        val marketingAgreed: Boolean
    )

    private val itemCache: MutableSharedFlow<ItemCache?> = MutableSharedFlow()
    private val launchFlow = itemCache.filterNotNull().onEach {
        +this
    }.launchIn(coroutineScope)

    fun plusAssign(
        socialType: String,
        socialToken: String,
        nickname: String,
        marketingAgreed: Boolean
    ) {
        coroutineScope.launch {
            itemCache.emit(
                ItemCache(
                    socialType, socialToken, nickname, marketingAgreed
                )
            )
        }
    }

    override suspend fun innerLogic(): NetworkResult<Boolean> = run {
        val currentCache = itemCache.single()
        currentCache?.let {
            if (userRepository.signUp(it.socialToken, it.socialType, it.nickname, it.marketingAgreed)) {
                NetworkResult.Success(true)
            } else {
                NetworkResult.Error(ClientExceptions(ClientError.LOGIN_NOT_VALIDATED_EXCEPTION))
            }
        } ?: NetworkResult.Error(ClientExceptions(ClientError.PARAMETER_NOT_AVAILABLE))
    }
}