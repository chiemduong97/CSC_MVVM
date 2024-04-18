package com.example.csc_mvvm.usecase.product

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.data.respository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val productRepository: ProductRepository) : ProductUseCaseSource {

    override suspend fun getByCategory(
        categoryId: Int,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> {
        return productRepository.requestGetByCategory(categoryId, page, limit)
    }

    override suspend fun filter(
        query: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> {
        return productRepository.requestFilter(query, page, limit)
    }

    override suspend fun getByUrl(
        url: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> {
        return productRepository.requestGetByUrl(url, page, limit)
    }
}