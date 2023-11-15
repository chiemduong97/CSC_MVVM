package com.example.csc_mvvm.data.respository.category

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepositorySource {
    suspend fun requestGetById(id: Int): Flow<Resource<CategoryModel>>
    suspend fun requestGetSuperCategories(): Flow<Resource<List<CategoryModel>>>
    suspend fun requestGetCategories(categoryId: Int): Flow<Resource<List<CategoryModel>>>
}