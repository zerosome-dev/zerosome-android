package com.zerosome.domain.profile

import com.zerosome.domain.repository.UserRepository
import com.zerosome.network.NetworkError
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RevokeUseCase @Inject constructor(
    private val profileRepository: UserRepository
) {

    operator fun invoke() = profileRepository.revoke().flatMapLatest {
        profileRepository.deleteAccessToken()
    }.map {
        if (it) {
            NetworkResult.Success(true)
        } else {
            NetworkResult.Error(NetworkError.INVALID_PARAMETER)
        }
    }
}