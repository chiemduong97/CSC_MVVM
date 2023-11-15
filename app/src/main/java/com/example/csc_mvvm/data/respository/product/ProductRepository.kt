package com.example.csc_mvvm.data.respository.product

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.data.local.LocalData
import com.example.csc_mvvm.data.remote.product.RemoteProductData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class ProductRepository(
    private val remoteData: RemoteProductData,
    private val localData: LocalData,
    private val context: CoroutineContext
) : ProductRepositorySource {
    override suspend fun requestGetByCategory(
        categoryId: Int,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> =
        flow {
            emit(remoteData.requestGetByCategory(categoryId, page, limit))
        }.flowOn(context)


    override suspend fun requestFilter(
        query: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> =
        flow {
            emit(remoteData.requestFilter(query, page, limit))
        }.flowOn(context)

    override suspend fun requestGetByUrl(
        url: String,
        page: Int,
        limit: Int
    ): Flow<Resource<List<ProductModel>>> =
        flow {
            emit(remoteData.requestGetByUrl(url, page, limit, localData.getUser().data?.id ?: -1))
        }.flowOn(context)

}