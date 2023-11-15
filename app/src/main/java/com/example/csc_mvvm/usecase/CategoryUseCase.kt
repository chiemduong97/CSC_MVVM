package com.example.csc_mvvm.usecase

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.category.CategoryResponse
import com.example.csc_mvvm.data.remote.ServiceGenerator
import com.example.csc_mvvm.data.remote.category.RemoteCategoryData
import com.example.csc_mvvm.data.remote.service.CategoryService
import com.example.csc_mvvm.data.respository.category.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class CategoryUseCase {
    private val categoryRepository by lazy {
        CategoryRepository(
            RemoteCategoryData(ServiceGenerator.newInstance().create(CategoryService::class.java)),
            Dispatchers.IO
        )
    }

    suspend fun getById(id: Int): Flow<Resource<CategoryModel>> {
        return categoryRepository.requestGetById(id)
    }

    suspend fun getSuperCategories(): Flow<Resource<List<CategoryModel>>> {
        return categoryRepository.requestGetSuperCategories()
    }

    suspend fun getCategories(categoryId: Int): Flow<Resource<List<CategoryModel>>> {
        return categoryRepository.requestGetCategories(categoryId)
    }
}