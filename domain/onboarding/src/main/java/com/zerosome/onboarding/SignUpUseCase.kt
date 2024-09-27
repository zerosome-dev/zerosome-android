package com.zerosome.onboarding

import com.zerosome.domain.repository.UserRepository
import com.zerosome.network.NetworkError
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(
        socialToken: String,
        socialType: String,
        nickname: String,
        marketingAgreed: Boolean
    ) = userRepository.signUp(socialToken, socialType, nickname, marketingAgreed).flatMapLatest {
        userRepository.checkUserLogin()
    }.map {
        if (it) {
            NetworkResult.Success(true)
        } else {
            NetworkResult.Error(NetworkError.UNAUTHORIZED)
        }
    }
}