package com.example.csc_mvvm.usecase.category

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.respository.category.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) :
    CategoryUseCaseSource {

    override suspend fun getById(id: Int): Flow<Resource<CategoryModel>> {
        return categoryRepository.requestGetById(id)
    }

    override suspend fun getSuperCategories(): Flow<Resource<List<CategoryModel>>> {
        return categoryRepository.requestGetSuperCategories()
    }

    override suspend fun getCategories(categoryId: Int): Flow<Resource<List<CategoryModel>>> {
        return categoryRepository.requestGetCategories(categoryId)
    }
}