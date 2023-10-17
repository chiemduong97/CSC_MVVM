package com.example.csc_mvvm.data.remote.category

import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.category.CategoryResponse
import com.example.csc_mvvm.data.dto.category.toCategories
import com.example.csc_mvvm.data.dto.category.toCategoriesResponse
import com.example.csc_mvvm.data.remote.service.CategoryService
import retrofit2.Call
import retrofit2.awaitResponse

class RemoteCategoryData(private val service: CategoryService) : RemoteCategoryDataSource {

    private suspend fun processCall(responseCall: () -> Call<*>): Any? {
        val response = responseCall.invoke().awaitResponse()
        return if (response.isSuccessful) {
            response.body()
        } else {
            response.code()
        }
    }

    override suspend fun requestGetById(id: Int): Resource<CategoryModel> {
        return when (val response = processCall { service.getById(id) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is CategoryResponse -> Resource.Success(data = (response.data as CategoryResponse).toCategoryModel())
                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestGetSuperCategories(): Resource<List<CategoryModel>> {
        return when (val response = processCall { service.getSuperCategories() }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(data = (response.data as List<*>).toCategoriesResponse().toCategories())
                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestGetCategories(categoryId: Int): Resource<List<CategoryModel>> {
        return when (val response = processCall { service.getCategories(categoryId) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(data = (response.data as List<*>).toCategoriesResponse().toCategories())
                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }
}