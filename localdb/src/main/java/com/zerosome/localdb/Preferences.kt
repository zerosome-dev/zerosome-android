package com.zerosome.localdb

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCE_NAME = "zerosome_preferences"

private val Context.datastore by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)