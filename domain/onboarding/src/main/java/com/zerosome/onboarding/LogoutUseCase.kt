package com.zerosome.onboarding

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<Boolean>() {

    override suspend fun innerLogic(): NetworkResult<Boolean> = run {
        if (userRepository.deleteAccessToken()) {
            NetworkResult.Success(true)
        } else {
            NetworkResult.Error(ClientExceptions(ClientError.LOGOUT_NOT_FINISHED))
        }
        NetworkResult.Success(userRepository.deleteAccessToken())
    }
}