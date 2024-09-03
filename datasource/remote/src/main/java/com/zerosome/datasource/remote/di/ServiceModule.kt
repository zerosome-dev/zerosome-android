package com.zerosome.datasource.remote.di

import com.zerosome.datasource.remote.service.AuthService
import com.zerosome.datasource.remote.service.CategoryService
import com.zerosome.datasource.remote.service.FilterService
import com.zerosome.datasource.remote.service.HomeService
import com.zerosome.datasource.remote.service.ProductService
import com.zerosome.datasource.remote.service.ReviewService
import com.zerosome.datasource.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Provides
    fun providesAuthService(@DefaultProvider httpClient: HttpClient): AuthService =
        AuthService(client = httpClient)

    @Provides
    fun providesCategoryService(@AuthProvider httpClient: HttpClient): CategoryService =
        CategoryService(httpClient)

    @Provides
    fun providesFilterService(@AuthProvider httpClient: HttpClient): FilterService =
        FilterService(httpClient)

    @Provides
    @Singleton
    fun providesHomeService(@AuthProvider httpClient: HttpClient): HomeService =
        HomeService(httpClient)

    @Provides
    @Singleton
    fun providesProductService(@AuthProvider httpClient: HttpClient): ProductService =
       ProductService(httpClient)

    @Provides
    @Singleton
    fun providesReviewService(@AuthProvider httpClient: HttpClient): ReviewService =
        ReviewService(httpClient)

    @Provides
    @Singleton
    fun providesUserService(@AuthProvider httpClient: HttpClient): UserService =
        UserService(httpClient)
}