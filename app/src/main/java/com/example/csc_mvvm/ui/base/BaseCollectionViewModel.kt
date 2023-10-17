package com.example.csc_mvvm.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseCollectionViewModel : BaseViewModel() {
    private var isLoadingMore = false
    protected var page = 1
    protected val limit = 10
    protected var loadMore = true
    private var _isLoadMoreLiveData = MutableLiveData<Boolean>()
    val isLoadMoreLiveData : LiveData<Boolean> get() = _isLoadMoreLiveData

    fun loadMore() {
        if (isLoadingMore || !loadMore) return
        isLoadingMore = true
        _isLoadMoreLiveData.value = true
        page++
        invokeLoadMore(page)
    }

    fun onRefresh() {
        page = 1
        loadMore = true
    }

    open fun onLoadMoreComplete() {
        if (isLoadingMore) _isLoadMoreLiveData.value = false
        isLoadingMore = false
    }

    protected open fun invokeLoadMore(page: Int) {}
}