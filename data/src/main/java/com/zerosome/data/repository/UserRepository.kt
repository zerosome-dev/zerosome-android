package com.zerosome.data.repository

import com.zerosome.data.service.AuthService
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {

    fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>>
}

internal class UserRepositoryImpl @Inject constructor(
    private val userService: AuthService
): UserRepository {
    override fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>> = userService.validateNickname(nickname)
}