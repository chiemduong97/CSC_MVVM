package com.example.csc_mvvm.usecase.category

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryUseCaseSource {

    suspend fun getById(id: Int): Flow<Resource<CategoryModel>>

    suspend fun getSuperCategories(): Flow<Resource<List<CategoryModel>>>

    suspend fun getCategories(categoryId: Int): Flow<Resource<List<CategoryModel>>>
}