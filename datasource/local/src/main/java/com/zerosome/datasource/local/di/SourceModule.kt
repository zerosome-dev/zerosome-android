package com.zerosome.datasource.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.zerosome.datasource.local.source.TokenSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class SourceModule {

    @Provides
    internal fun providesUserStore(@ApplicationContext context: Context) = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile("zerosome_preference")
        }
    )

    @Provides
    @Singleton
    internal fun providesTokenSource(dataStore: DataStore<Preferences>): TokenSource =
        TokenSource(dataStore)
}