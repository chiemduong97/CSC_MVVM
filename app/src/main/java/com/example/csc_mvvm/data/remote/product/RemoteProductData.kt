package com.example.csc_mvvm.data.remote.product

import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.cart.CartModel
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.data.dto.product.checkCart
import com.example.csc_mvvm.data.dto.product.toProducts
import com.example.csc_mvvm.data.dto.product.toProductsResponse
import com.example.csc_mvvm.data.remote.ServiceGenerator
import com.example.csc_mvvm.data.remote.service.ProductService
import retrofit2.Call
import retrofit2.awaitResponse
import javax.inject.Inject

class RemoteProductData @Inject constructor(private val serviceGenerator: ServiceGenerator) : RemoteProductDataSource {

    private val service by lazy { serviceGenerator.newInstance().create(ProductService::class.java) }
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
        limit: Int,
        cart: CartModel
    ): Resource<List<ProductModel>> {
        return when (val response =
            processCall { service.getByCategory(categoryId, page, limit) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(
                        data = (response.data as List<*>).toProductsResponse().toProducts().checkCart(cart),
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
        limit: Int,
        cart: CartModel
    ): Resource<List<ProductModel>> {
        return when (val response = processCall { service.filter(query, page, limit) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(
                        data = (response.data as List<*>).toProductsResponse().toProducts().checkCart(cart),
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
        userId: Int,
        cart: CartModel
    ): Resource<List<ProductModel>> {
        return when (val response = processCall { service.getByUrl(url, page, limit, userId) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(
                        data = (response.data as List<*>).toProductsResponse().toProducts().checkCart(cart),
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