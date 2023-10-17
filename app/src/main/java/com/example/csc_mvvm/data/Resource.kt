package com.example.csc_mvvm.data

open class Resource<T>(
    val data: T? = null,
    val errorCode: Int? = null,
    val isLoadMore: Boolean = false
) {
    class Success<T>(data: T, isLoadMore: Boolean = false) : Resource<T>(data = data, isLoadMore = isLoadMore)
    class Loading<T> : Resource<T>()
    class DataError<T>(errorCode: Int) : Resource<T>(null, errorCode)
}

