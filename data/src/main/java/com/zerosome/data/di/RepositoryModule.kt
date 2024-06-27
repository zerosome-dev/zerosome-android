package com.zerosome.data.di

import com.zerosome.data.repository.UserRepository
import com.zerosome.data.repository.UserRepositoryImpl
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
}