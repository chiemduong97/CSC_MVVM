package com.example.csc_mvvm.data.remote.product

import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.data.dto.product.toProducts
import com.example.csc_mvvm.data.dto.product.toProductsResponse
import com.example.csc_mvvm.data.remote.service.ProductService
import retrofit2.Call
import retrofit2.awaitResponse

class RemoteProductData(private val service: ProductService) : RemoteProductDataSource {

    private suspend fun processCall(responseCall: () -> Call<*>): Any? {
        val response = responseCall.invoke().awaitResponse()
        return if (response.isSuccessful) {
            response.body()
        } else {
            response.code()
        }
    }

    override suspend fun requestGetByCategory(
        categoryId: Int,
        page: Int,
        limit: Int
    ): Resource<List<ProductModel>> {
        return when (val response =
            processCall { service.getByCategory(categoryId, page, limit) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(
                        data = (response.data as List<*>).toProductsResponse().toProducts(),
                        isLoadMore = response.loadMore
                    )
                    else -> Resource.DataError(errorCode = 1001)
                }
            }
            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestFilter(
        query: String,
        page: Int,
        limit: Int
    ): Resource<List<ProductModel>> {
        return when (val response = processCall { service.filter(query, page, limit) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(
                        data = (response.data as List<*>).toProductsResponse().toProducts(),
                        isLoadMore = response.loadMore
                    )
                    else -> Resource.DataError(errorCode = 1001)
                }
            }
            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestGetByUrl(
        url: String,
        page: Int,
        limit: Int,
        userId: Int
    ): Resource<List<ProductModel>> {
        return when (val response = processCall { service.getByUrl(url, page, limit, userId) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(
                        data = (response.data as List<*>).toProductsResponse().toProducts(),
                        isLoadMore = response.loadMore
                    )
                    else -> Resource.DataError(errorCode = 1001)
                }
            }
            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }
}