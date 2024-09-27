package com.zerosome.onboarding

import com.zerosome.domain.repository.UserRepository
import com.zerosome.network.NetworkError
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.logout().flatMapLatest {
        userRepository.deleteAccessToken()
    }.map {
        if (it) {
            NetworkResult.Success(true)
        } else {
            NetworkResult.Error(NetworkError.INVALID_PARAMETER)
        }
    }
}