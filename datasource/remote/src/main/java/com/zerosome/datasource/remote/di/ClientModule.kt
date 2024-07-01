package com.zerosome.datasource.remote.di

import com.zerosome.datasource.local.source.TokenSource
import com.zerosome.datasource.local.entity.TokenEntity
import com.zerosome.datasource.remote.dto.response.TokenResponse
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
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesKtorClient(
        dataSource: TokenSource
    ): HttpClient = HttpClient(Android) {
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(Json {
                this.prettyPrint = true
            })
        }
        install(Auth) {
            val accessToken = runBlocking { dataSource.getAccessToken().firstOrNull() }
            accessToken?.let {
                bearer {
                    refreshTokens {
                        val token = client.get {
                            markAsRefreshTokenRequest()
                            url("/api/v1/auth/refresh")
                            setBody(TokenEntity(it.accessToken, it.refreshToken))
                        }.body<TokenResponse>()

                        BearerTokens(token.accessToken, refreshToken = token.refreshToken)
                    }

                }
            }
        }
    }
}


@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class AuthQualifier

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class NoAuthQualifier