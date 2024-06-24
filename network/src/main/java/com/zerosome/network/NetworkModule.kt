package com.zerosome.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesKtorClient(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                this.prettyPrint = true
            })
        }
    }
}