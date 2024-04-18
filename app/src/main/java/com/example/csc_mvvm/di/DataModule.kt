package com.example.csc_mvvm.di

import com.example.csc_mvvm.usecase.branch.BranchUseCase
import com.example.csc_mvvm.usecase.branch.BranchUseCaseSource
import com.example.csc_mvvm.usecase.category.CategoryUseCase
import com.example.csc_mvvm.usecase.category.CategoryUseCaseSource
import com.example.csc_mvvm.usecase.product.ProductUseCase
import com.example.csc_mvvm.usecase.product.ProductUseCaseSource
import com.example.csc_mvvm.usecase.user.UserUseCase
import com.example.csc_mvvm.usecase.user.UserUseCaseSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Tells Dagger this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideUserUseCase(userUseCase: UserUseCase): UserUseCaseSource


    @Binds
    @Singleton
    abstract fun provideProductUseCase(productUseCase: ProductUseCase): ProductUseCaseSource

    @Binds
    @Singleton
    abstract fun provideBranchUseCase(branchUseCase: BranchUseCase): BranchUseCaseSource

    @Binds
    @Singleton
    abstract fun provideCategoryUseCase(categoryUseCase: CategoryUseCase): CategoryUseCaseSource
}
