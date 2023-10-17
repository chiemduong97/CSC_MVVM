package com.example.csc_mvvm.data.respository.category

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepositorySource {
    fun requestGetById(id: Int): Flow<Resource<CategoryModel>>
    fun requestGetSuperCategories(): Flow<Resource<List<CategoryModel>>>
    fun requestGetCategories(categoryId: Int): Flow<Resource<List<CategoryModel>>>
}