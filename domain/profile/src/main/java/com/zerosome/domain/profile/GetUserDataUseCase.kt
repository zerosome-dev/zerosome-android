package com.zerosome.domain.profile

import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.model.UserBasicInfo
import com.zerosome.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<UserBasicInfo>() {
    override suspend fun innerLogic(): NetworkResult<UserBasicInfo> = NetworkResult.Success(userRepository.getUserData())
}