package com.example.csc_mvvm.usecase

import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.data.local.LocalData
import com.example.csc_mvvm.data.remote.ServiceGenerator
import com.example.csc_mvvm.data.remote.product.RemoteProductData
import com.example.csc_mvvm.data.remote.service.ProductService
import com.example.csc_mvvm.data.respository.product.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ProductUseCase {
    private val productRepository by lazy {
        ProductRepository(
            RemoteProductData(ServiceGenerator.newInstance().create(ProductService::class.java)),
            LocalData(Preferences.newInstance()),
            Dispatchers.IO
        )
    }

    suspend fun getByCategory(
        categoryId: Int,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> {
        return productRepository.requestGetByCategory(categoryId, page, limit)
    }

    suspend fun filter(
        query: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> {
        return productRepository.requestFilter(query, page, limit)
    }

    suspend fun getByUrl(
        url: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> {
        return productRepository.requestGetByUrl(url, page, limit)
    }
}