package com.zerosome.datasource.remote.di

//import com.zerosome.datasource.remote.service.FilterService
//import com.zerosome.datasource.remote.service.HomeService
//import com.zerosome.datasource.remote.service.ProductService
//import com.zerosome.datasource.remote.service.ReviewService
import com.zerosome.datasource.remote.service.AuthService
import com.zerosome.datasource.remote.service.CategoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Provides
    fun providesAuthService(httpClient: HttpClient): AuthService =
        AuthService(client = httpClient)

    @Provides
    fun providesCategoryService(httpClient: HttpClient): CategoryService =
        CategoryService(httpClient)

//    @Provides
//    fun providesFilterService(httpClient: HttpClient): FilterService =
//        FilterService(httpClient)
//
//    @Provides
//    @Singleton
//    fun providesHomeService(httpClient: HttpClient): HomeService =
//        HomeService(httpClient)
//
//    @Provides
//    fun providesProductService(httpClient: HttpClient): ProductService =
//       ProductService(httpClient)
//
//    @Provides
//    fun providesReviewService(httpClient: HttpClient): ReviewService =
//        ReviewService(httpClient)
}