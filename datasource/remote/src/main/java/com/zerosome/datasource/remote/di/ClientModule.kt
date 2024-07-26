package com.zerosome.datasource.remote.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesKtorClient(): HttpClient = HttpClient(Android) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = "15.164.6.36:8080"
            }
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(Json {
                this.prettyPrint = true
            })
        }
        install(Logging) {
            this.level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("CPRI", message)
                }
            }
        }
//        install(Auth) {
//            val accessToken = runBlocking { dataSource.getAccessToken().firstOrNull() }
//            accessToken?.let {
//                bearer {
//                    refreshTokens {
//                        val token = client.get {
//                            markAsRefreshTokenRequest()
//                            url("/api/v1/auth/refresh")
//                            setBody(TokenEntity(it.accessToken, it.refreshToken))
//                        }.body<TokenResponse>()
//
//                        BearerTokens(token.accessToken, refreshToken = token.refreshToken)
//                    }
//
//                }
//            }
//        }
    }
}