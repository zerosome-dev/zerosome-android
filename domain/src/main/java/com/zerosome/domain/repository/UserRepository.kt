package com.zerosome.domain.repository

import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun validateNickname(nickname: String): Flow<NetworkResult<Boolean>>

    fun signUp(socialToken: String, socialType: String, nickname: String, marketingAgreement: Boolean): Flow<NetworkResult<Unit>>

    fun login(socialToken: String, socialType: String): Flow<NetworkResult<Boolean>>

    fun checkUserLogin(): Flow<Boolean>

    fun deleteAccessToken(): Flow<Boolean>

    fun revoke(): Flow<NetworkResult<Boolean>>
}