package com.zerosome.data.di

import com.zerosome.data.service.AuthService
import com.zerosome.data.service.CategoryService
import com.zerosome.data.service.FilterService
import com.zerosome.data.service.HomeService
import com.zerosome.data.service.ProductService
import com.zerosome.data.service.ReviewService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Provides
    fun providesAuthService(httpClient: HttpClient): AuthService = AuthService(client = httpClient)

    @Provides
    fun providesCategoryService(httpClient: HttpClient): CategoryService = CategoryService(httpClient)

    @Provides
    fun providesFilterService(httpClient: HttpClient): FilterService = FilterService(httpClient)

    @Provides
    fun providesHomeService(httpClient: HttpClient): HomeService = HomeService(httpClient)

    @Provides
    fun providesProductService(httpClient: HttpClient): ProductService = ProductService(httpClient)

    @Provides
    fun providesReviewService(httpClient: HttpClient): ReviewService = ReviewService(httpClient)
}