package com.zerosome.onboarding

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.repository.UserRepository
import javax.inject.Inject

class RevokeUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<Boolean>() {

    override suspend fun innerLogic(): NetworkResult<Boolean> = run {
        if (userRepository.revoke()) {
            NetworkResult.Success(true)
        } else {
            NetworkResult.Error(ClientExceptions(ClientError.NETWORK_UNVALIDATED_INVALID))
        }
    }

}