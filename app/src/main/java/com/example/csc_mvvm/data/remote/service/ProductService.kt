package com.example.csc_mvvm.data.remote.service

import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.dto.product.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ProductService {
    @GET("api/product/product_getAll.php")
    fun getByCategory(
        @Query("category_id") categoryId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Call<BaseResponse<List<ProductResponse>>>

    @GET("api/product/product_search.php")
    fun filter(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Call<BaseResponse<List<ProductResponse>>>

    @GET
    fun getByUrl(
        @Url url: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("user_id") userId: Int
    ): Call<BaseResponse<List<ProductResponse>>>
}