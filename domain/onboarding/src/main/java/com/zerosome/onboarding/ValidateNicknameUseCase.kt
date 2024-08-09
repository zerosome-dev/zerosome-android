package com.zerosome.onboarding

import com.zerosome.domain.repository.UserRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ValidateNicknameUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(nickname: String): Flow<NetworkResult<ValidateReason>> = flow {
        emit(NetworkResult.Loading)
    }.flatMapMerge {
        if (nickname.length in 2..12) {
            flowOf(nickname)
        } else {
            flowOf(null)
        }
    }.flatMapLatest { name ->
        name?.let {
            repository.validateNickname(nickname).map {
                when(it) {
                    NetworkResult.Loading -> NetworkResult.Loading
                    is NetworkResult.Success -> NetworkResult.Success(if (it.data) ValidateReason.SUCCESS else ValidateReason.NOT_VERIFIED)
                    is NetworkResult.Error -> NetworkResult.Error(it.error)
                }
            }
        } ?: flowOf(NetworkResult.Success(ValidateReason.NOT_VALIDATED))
    }
}