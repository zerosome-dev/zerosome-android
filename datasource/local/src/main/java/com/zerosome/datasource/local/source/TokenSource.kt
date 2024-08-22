package com.zerosome.datasource.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zerosome.datasource.local.entity.TokenEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class TokenSource(
    private val tokenPreferenceStore: DataStore<Preferences>,
) {
    private companion object {
        val ACCESS_TOKEN  = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    fun getAccessToken(): Flow<TokenEntity?> = tokenPreferenceStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preference ->
        getToken(preference)
    }

    private fun getToken(preferences: Preferences): TokenEntity? {
        val accessToken = preferences[ACCESS_TOKEN]
        val refreshToken = preferences[REFRESH_TOKEN]
        return if (accessToken != null && refreshToken != null) {
            TokenEntity(accessToken, refreshToken)
        } else null
    }

    suspend fun updateToken(accessToken: String? = null, refreshToken: String? = null): Boolean {
        try {
            tokenPreferenceStore.edit {
                accessToken?.let { at ->
                    it[ACCESS_TOKEN] = at
                }
                refreshToken?.let { rt ->
                    it[REFRESH_TOKEN] = rt
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }
}