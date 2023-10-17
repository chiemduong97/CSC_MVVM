package com.example.csc_mvvm.data.remote.service


import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.dto.category.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryService {
    @GET("api/category/category_getById.php")
    fun getById(@Query("id") id: Int): Call<BaseResponse<CategoryResponse>>

    @GET("api/category/category_getLevel_0.php")
    fun getSuperCategories(): Call<BaseResponse<List<CategoryResponse>>>

    @GET("api/category/category_getLevel_1.php")
    fun getCategories(@Query("category_id") categoryId: Int): Call<BaseResponse<List<CategoryResponse>>>
}