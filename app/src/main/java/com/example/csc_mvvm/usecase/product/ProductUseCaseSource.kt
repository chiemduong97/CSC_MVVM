package com.example.csc_mvvm.usecase.product

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.product.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductUseCaseSource {
    suspend fun getByCategory(
        categoryId: Int,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>>

    suspend fun filter(
        query: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>>

    suspend fun getByUrl(
        url: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>>
}