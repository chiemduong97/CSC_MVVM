package com.example.csc_mvvm.data.respository.product

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.product.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepositorySource {
    suspend fun requestGetByCategory(
        categoryId: Int,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>>

    suspend fun requestFilter(
        query: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>>

    suspend fun requestGetByUrl(
        url: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>>

}