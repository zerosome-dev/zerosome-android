package com.zerosome.datasource.remote.di

import android.util.Log
import com.zerosome.datasource.local.entity.TokenEntity
import com.zerosome.datasource.local.source.TokenSource
import com.zerosome.datasource.remote.dto.response.TokenResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.NetworkError
import com.zerosome.network.ZSNetworkException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @DefaultProvider
    @Singleton
    @Provides
    fun providesDefaultProvider(): HttpClient = HttpClient(Android) {
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
                    polymorphic(BaseResponse::class) {}
                }
            })
        }
//        HttpResponseValidator {
//            validateResponse {
//                val body = it.body<BaseResponse<Any>>()
//                if (body.status.not()) {
//                    throw ZSNetworkException(NetworkError.from(body.code))
//                }
//            }
//        }
        install(Logging) {
            this.level = LogLevel.ALL
            logger = Logger.ANDROID
        }
    }

    @Singleton
    @AuthProvider
    @Provides
    fun providesKtorClient(
        dataSource: TokenSource
    ): HttpClient = HttpClient(Android) {

        install(Auth) {
            bearer {
                loadTokens {
                    val token = dataSource.getTokenEntity()
                    BearerTokens(token?.accessToken ?: "", token?.refreshToken ?: "")
                }
                refreshTokens {
                    val refreshToken = client.post(
                        urlString = "http://15.164.6.36:8080/api/v1/auth/refresh",
                    ) {
                        val token = dataSource.getTokenEntity()
                        markAsRefreshTokenRequest()
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                        setBody(
                            TokenEntity(
                                token?.accessToken ?: "",
                                token?.refreshToken ?: ""
                            )
                        )
                    }.body<BaseResponse<TokenResponse>>()

                    BearerTokens(
                        accessToken = refreshToken.data?.accessToken ?: "",
                        refreshToken = refreshToken.data?.refreshToken ?: ""
                    )
                        .also {
                            dataSource.updateToken(
                                accessToken = refreshToken.data?.accessToken ?: "",
                                refreshToken = refreshToken.data?.refreshToken ?: " "
                            ).also {
                                Log.d("CPRI", "UPDATE : $it")
                            }
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
//        HttpResponseValidator {
//            validateResponse {
//                val body = it.body<BaseResponse<*>>()
//                if (body.status.not()) {
//                    throw ZSNetworkException(NetworkError.from(body.code))
//                }
//            }
//        }
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
            logger = Logger.ANDROID
        }


    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthProvider

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultProvider