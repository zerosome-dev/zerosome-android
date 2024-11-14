package com.zerosome.domain.repository

import com.zerosome.domain.model.UserBasicInfo

interface UserRepository {

    suspend fun validateNickname(nickname: String): Boolean

    suspend fun signUp(socialToken: String, socialType: String, nickname: String, marketingAgreement: Boolean): Boolean

    suspend fun login(socialToken: String, socialType: String): Boolean

    suspend fun checkUserLogin(): Boolean

    suspend fun deleteAccessToken(): Boolean

    suspend fun getUserData(): UserBasicInfo

    suspend fun revoke(): Boolean
}