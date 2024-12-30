package com.example.formusingroomdemo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository

}