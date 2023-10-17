package com.example.csc_mvvm.data.respository.category

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.remote.category.RemoteCategoryData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class CategoryRepository(
    private val remoteData: RemoteCategoryData, private val context: CoroutineContext
) : CategoryRepositorySource {

    override fun requestGetById(id: Int): Flow<Resource<CategoryModel>> =
        flow {
            emit(remoteData.requestGetById(id))
        }.flowOn(context)

    override fun requestGetSuperCategories(): Flow<Resource<List<CategoryModel>>> =
        flow {
            emit(remoteData.requestGetSuperCategories())
        }.flowOn(context)

    override fun requestGetCategories(categoryId: Int): Flow<Resource<List<CategoryModel>>> =
        flow {
            emit(remoteData.requestGetCategories(categoryId))
        }.flowOn(context)

}