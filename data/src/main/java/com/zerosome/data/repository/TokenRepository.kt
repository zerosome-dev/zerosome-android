package com.zerosome.data.repository

import com.zerosome.datasource.local.entity.TokenEntity
import com.zerosome.datasource.local.source.TokenSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TokenRepository {

    fun getToken(): Flow<TokenEntity?>

    suspend fun updateToken(accessToken: String? = null, refreshToken: String? = null)
}

class TokenRepositoryImpl @Inject constructor(
    private val source: TokenSource
): TokenRepository {
    override fun getToken(): Flow<TokenEntity?> = source.getAccessToken()



    override suspend fun updateToken(accessToken: String?, refreshToken: String?) = source.updateToken(accessToken, refreshToken)

}