package com.zerosome.datasource.local.source

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zerosome.datasource.local.entity.TokenEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class TokenSource(
    private val tokenPreferenceStore: DataStore<Preferences>,
) {
    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun getTokenEntity(): TokenEntity? {
        val accessToken = tokenPreferenceStore.data.firstOrNull()?.get(ACCESS_TOKEN)
        val refreshToken = tokenPreferenceStore.data.firstOrNull()?.get(REFRESH_TOKEN)
        Log.d("CPRI", "ACCESS : $accessToken REFRESH :$refreshToken")
        return if (accessToken != null && refreshToken != null) {
            TokenEntity(accessToken, refreshToken)
        } else null
    }

    suspend fun updateToken(accessToken: String? = null, refreshToken: String? = null): Boolean {
        Log.d("CPRI", "UPDATE TOKEN :$accessToken REFRESH TOKEN :$refreshToken")
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