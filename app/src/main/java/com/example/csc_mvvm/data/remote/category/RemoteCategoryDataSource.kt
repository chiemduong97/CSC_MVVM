package com.example.csc_mvvm.data.remote.category

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel

interface RemoteCategoryDataSource {
    suspend fun requestGetById(id: Int): Resource<CategoryModel>

    suspend fun requestGetSuperCategories(): Resource<List<CategoryModel>>

    suspend fun requestGetCategories(categoryId: Int): Resource<List<CategoryModel>>

}