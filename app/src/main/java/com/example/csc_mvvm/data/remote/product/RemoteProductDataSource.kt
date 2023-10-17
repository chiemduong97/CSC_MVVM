package com.example.csc_mvvm.data.remote.product

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.product.ProductModel

interface RemoteProductDataSource {
    suspend fun requestGetByCategory(
        categoryId: Int,
        page: Int,
        limit: Int
    ): Resource<List<ProductModel>>

    suspend fun requestFilter(
        query: String,
        page: Int,
        limit: Int
    ): Resource<List<ProductModel>>

    suspend fun requestGetByUrl(
        url: String,
        page: Int,
        limit: Int,
        userId: Int
    ): Resource<List<ProductModel>>


}