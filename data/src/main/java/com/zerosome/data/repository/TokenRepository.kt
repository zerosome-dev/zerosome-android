package com.zerosome.data.repository

import com.zerosome.datasource.local.source.TokenSource
import javax.inject.Inject

interface TokenRepository {

    suspend fun updateToken(accessToken: String? = null, refreshToken: String? = null)
}

class TokenRepositoryImpl @Inject constructor(
    private val source: TokenSource
): TokenRepository {
    override suspend fun updateToken(accessToken: String?, refreshToken: String?) = source.updateToken(accessToken, refreshToken)

}