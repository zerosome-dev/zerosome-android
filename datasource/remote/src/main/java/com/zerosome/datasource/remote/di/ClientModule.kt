package com.zerosome.datasource.remote.di

import android.util.Log
import com.zerosome.datasource.local.entity.TokenEntity
import com.zerosome.datasource.local.source.TokenSource
import com.zerosome.datasource.remote.dto.response.TokenResponse
import com.zerosome.network.BaseResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesKtorClient(
        dataSource: TokenSource
    ): HttpClient = HttpClient(Android) {

        install(Auth) {
            bearer {
                val accessToken = runBlocking { dataSource.getAccessToken().firstOrNull() }
                Log.d("CPRI", "ACCESS TOKEN $accessToken")
                accessToken?.accessToken?.let { _ ->
                    loadTokens {
                        BearerTokens(accessToken.accessToken, accessToken.refreshToken)
                    }
                }
                refreshTokens {
                    val refreshToken = client.get(
                        urlString = "http://15.164.6.36:8080/api/v1/auth/refresh",
                    ) {
                        markAsRefreshTokenRequest()
                        contentType(ContentType.Application.Json)
                        setBody(
                            TokenEntity(
                                accessToken?.accessToken ?: "",
                                accessToken?.refreshToken ?: ""
                            )
                        )
                    }.body<BaseResponse<TokenResponse>>()

                    BearerTokens(
                        "Bearer ${refreshToken.data?.accessToken}",
                        refreshToken = refreshToken.data?.refreshToken ?: ""
                    )
                        .also {
                            dataSource.updateToken(
                                accessToken = refreshToken.data?.accessToken ?: "",
                                refreshToken = refreshToken.data?.refreshToken ?: " "
                            )
                        }
                }
            }
        }
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
                ignoreUnknownKeys = true
                encodeDefaults = true
                serializersModule = SerializersModule {
                }
            })
        }
        install(Logging) {
            this.level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("HTTP-REQUEST", message)
                }
            }
        }


    }
}
