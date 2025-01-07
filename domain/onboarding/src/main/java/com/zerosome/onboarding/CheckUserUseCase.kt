package com.zerosome.onboarding

import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.repository.UserRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<Boolean>() {

    override suspend fun innerLogic(): NetworkResult<Boolean> =
        NetworkResult.Success(userRepository.checkUserLogin())


    override operator fun unaryPlus() {
        coroutineScope.launch {
            callLogic()
        }
    }
}