package com.example.csc_mvvm.di

import com.example.csc_mvvm.data.error.mapper.ErrorMapper
import com.example.csc_mvvm.data.error.mapper.ErrorMapperSource
import com.example.csc_mvvm.usecase.errors.ErrorManager
import com.example.csc_mvvm.usecase.errors.ErrorUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// with @Module we Telling Dagger that, this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}
