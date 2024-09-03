package com.zerosome.data.di

import com.zerosome.data.repository.CategoryRepositoryImpl
import com.zerosome.data.repository.FilterRepositoryImpl
import com.zerosome.data.repository.HomeRepositoryImpl
import com.zerosome.data.repository.ProductRepositoryImpl
import com.zerosome.data.repository.ReviewRepositoryImpl
import com.zerosome.data.repository.UserRepositoryImpl
import com.zerosome.domain.repository.CategoryRepository
import com.zerosome.domain.repository.FilterRepository
import com.zerosome.domain.repository.HomeRepository
import com.zerosome.domain.repository.ProductRepository
import com.zerosome.domain.repository.ReviewRepository
import com.zerosome.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindsHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    fun bindsCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindsProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    fun bindsReviewRepository(impl: ReviewRepositoryImpl): ReviewRepository

    @Binds
    @Singleton
    fun bindsFilterRepository(impl: FilterRepositoryImpl): FilterRepository
}